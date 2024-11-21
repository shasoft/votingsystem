package ru.shasoft.votingsystem.vote;

import ru.shasoft.votingsystem.MatcherFactory;
import ru.shasoft.votingsystem.vote.model.Vote;

import java.time.LocalDate;

import static ru.shasoft.votingsystem.menu.MenuTestData.date2024_11_05;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_2;
import static ru.shasoft.votingsystem.user.UserTestData.ADMIN_ID;
import static ru.shasoft.votingsystem.user.UserTestData.USER_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class);

    public static final int VOTE_ID_1 = 1;
    public static final int NOT_FOUND = 100;

    public static final Vote vote1 = new Vote(VOTE_ID_1, date2024_11_05, USER_ID, RESTAURANT_ID_1);
    public static final Vote voteNotLike = new Vote(NOT_FOUND, date2024_11_05, ADMIN_ID, RESTAURANT_ID_2);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), USER_ID, RESTAURANT_ID_1);
    }
}
