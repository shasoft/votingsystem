package ru.shasoft.votingsystem.vote.object;

import java.time.LocalDate;

public class VoteCount {

    public LocalDate createAt;
    public Integer restaurantId;
    public Long count;

    public VoteCount(LocalDate createAt, Integer restaurantId, Long count) {
        this.createAt = createAt;
        this.restaurantId = restaurantId;
        this.count = count;
    }

    @Override
    public String toString() {
        return "VoteCount(" + createAt.toString() + ", " + restaurantId + ") = " + count;
    }
}