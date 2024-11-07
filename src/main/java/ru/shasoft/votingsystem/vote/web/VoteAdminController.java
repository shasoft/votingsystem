package ru.shasoft.votingsystem.vote.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.shasoft.votingsystem.vote.model.Vote;

@RestController
@RequestMapping(value = VoteAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
// TODO: cache only most requested, seldom changed data!
public class VoteAdminController extends AbstractVoteController {

    static final String REST_URL = "/api/admin/vote";

    @GetMapping
    public boolean get(@Valid @RequestBody Vote vote) {
        return repository.findVote(vote.getRestaurantId(), vote.getCreateAt(), vote.getUserId()).isPresent();
    }

    @PostMapping(value = "/like", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@Valid @RequestBody Vote vote) {
        repository.like(vote, 1);
    }

    @DeleteMapping(value = "/unlike", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void unlike(@Valid @RequestBody Vote vote) {
        repository.like(vote, -1);
    }
}