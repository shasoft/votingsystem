package ru.shasoft.votingsystem.vote.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shasoft.votingsystem.vote.repository.VoteRepository;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractVoteController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected VoteRepository repository;
}