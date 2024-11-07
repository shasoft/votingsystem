package ru.shasoft.votingsystem.vote.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.shasoft.votingsystem.app.AuthUser;
import ru.shasoft.votingsystem.common.error.IllegalRequestDataException;
import ru.shasoft.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
// TODO: cache only most requested, seldom changed data!
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/vote";

    @GetMapping("/{restaurant_id}")
    public boolean get(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        return repository.findVote(restaurant_id, LocalDate.now(), authUser.id()).isPresent();
    }

    @PutMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        likeSign(restaurant_id, authUser, 1);
    }

    @DeleteMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        likeSign(restaurant_id, authUser, -1);
    }

    private void likeSign(int restaurant_id, AuthUser authUser, int sign) {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
            throw new IllegalRequestDataException("It's too late to vote.");
        }
        final Vote vote = new Vote(null, restaurant_id, LocalDate.now(), authUser.id());
        log.info("vote {}", vote);
        repository.like(vote, sign);
    }
}