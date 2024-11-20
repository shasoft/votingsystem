package ru.shasoft.votingsystem.vote.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shasoft.votingsystem.app.AuthUser;
import ru.shasoft.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/vote";

    @GetMapping("/{restaurant_id}")
    public boolean get(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        final Optional<Vote> ret = repository.findVote(restaurant_id, LocalDate.now(), authUser.id());
        return ret.isPresent();
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