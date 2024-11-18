package ru.shasoft.votingsystem.vote;

import ru.shasoft.votingsystem.MatcherFactory;
import ru.shasoft.votingsystem.vote.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.shasoft.votingsystem.common.validation.ValidationUtil.VOTE_END_TIME;
import static ru.shasoft.votingsystem.menu.MenuTestData.date2024_11_05;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_2;
import static ru.shasoft.votingsystem.user.UserTestData.ADMIN_ID;
import static ru.shasoft.votingsystem.user.UserTestData.USER_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class);

    public static final LocalTime timeBefore = VOTE_END_TIME.minusMinutes(1);
    public static final LocalTime timeAfter = VOTE_END_TIME.plusMinutes(1);

    public static final int VOTE_ID_1 = 1;
    public static final int NOT_FOUND = 100;

    public static final Vote vote1 = new Vote(VOTE_ID_1, RESTAURANT_ID_1, date2024_11_05, USER_ID);
    public static final Vote voteNotLike = new Vote(NOT_FOUND, RESTAURANT_ID_2, date2024_11_05, ADMIN_ID);

    public static Vote getNew() {
        return new Vote(null, RESTAURANT_ID_1, LocalDate.now(), USER_ID);
    }
}
