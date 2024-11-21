package ru.shasoft.votingsystem.vote.web;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/vote";
    @Autowired
    protected RestaurantRepository restaurantRepository;

    @GetMapping()
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
/*
    @PutMapping(value = "/{restaurant_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        ValidationUtil.checkUpdateLike(authUser);
        repository.like(restaurant_id, LocalDate.now(), authUser.id());
    }

    @DeleteMapping(value = "/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        ValidationUtil.checkUpdateLike(authUser);
        repository.unlike(restaurant_id, LocalDate.now(), authUser.id());
    }
 */
}