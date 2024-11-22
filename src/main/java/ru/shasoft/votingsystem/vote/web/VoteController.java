package ru.shasoft.votingsystem.vote.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.shasoft.votingsystem.app.AuthUser;
import ru.shasoft.votingsystem.common.error.IllegalRequestDataException;
import ru.shasoft.votingsystem.common.validation.ValidationUtil;
import ru.shasoft.votingsystem.restaurant.repository.RestaurantRepository;
import ru.shasoft.votingsystem.user.model.Role;
import ru.shasoft.votingsystem.vote.model.Vote;
import ru.shasoft.votingsystem.vote.object.VoteCount;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api";
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @PutMapping("/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void vote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        if (!authUser.hasRole(Role.USER)) {
            throw new IllegalRequestDataException("No permission to perform the operation");
        }
        restaurantRepository.getExisted(restaurantId);
        final LocalDate nowDate = LocalDate.now();
        final Optional<Vote> ret = repository.findVote(nowDate, authUser.id());
        if (ret.isPresent()) {
            if (LocalTime.now().isAfter(ValidationUtil.VOTE_END_TIME)) {
                throw new IllegalRequestDataException("It's too late to vote.");
            }
            Vote vote = ret.get();
            vote.setRestaurantId(restaurantId);
            repository.save(vote);
        } else {
            Vote vote = new Vote(null, nowDate, authUser.id(), restaurantId);
            repository.save(vote);
        }
    }

    @GetMapping("/votes")
    @Transactional
    @Cacheable("votes")
    public List<Vote> votes(@RequestParam @Nullable Integer limit, @AuthenticationPrincipal AuthUser authUser) {
        if (limit == null) {
            limit = 32;
        }
        return repository.getByUser(authUser.id(), limit);
    }

    @GetMapping("/votes/by-restaurant")
    @Transactional
    @Cacheable("votes-by-restaurant")
    public List<VoteCount> byRestaurant(@RequestParam Integer restaurantId) {
        return repository.getByRestaurant(restaurantId);
    }

    @GetMapping("/votes/by-date")
    @Transactional
    @Cacheable("votes-by-date")
    public List<VoteCount> byDate(@RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return repository.getByDate(date);
    }
}