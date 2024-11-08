package ru.shasoft.votingsystem.vote.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.shasoft.votingsystem.app.AuthUser;
import ru.shasoft.votingsystem.common.error.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
// TODO: cache only most requested, seldom changed data!
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/vote";
    static final int VOTE_END_HOUR = 11;

    @GetMapping("/{restaurant_id}")
    public boolean get(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        return repository.findVote(restaurant_id, LocalDate.now(), authUser.id()).isPresent();
    }

    @PutMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        if (LocalTime.now().isAfter(LocalTime.of(VOTE_END_HOUR, 0))) {
            throw new IllegalRequestDataException("It's too late to vote.");
        }
        repository.like(restaurant_id, LocalDate.now(), authUser.id());
    }

    @DeleteMapping(value = "/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        if (LocalTime.now().isAfter(LocalTime.of(VOTE_END_HOUR, 0))) {
            throw new IllegalRequestDataException("It's too late to vote.");
        }
        repository.unlike(restaurant_id, LocalDate.now(), authUser.id());
    }
}