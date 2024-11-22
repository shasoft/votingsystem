package ru.shasoft.votingsystem.vote;

import ru.shasoft.votingsystem.MatcherFactory;
import ru.shasoft.votingsystem.vote.model.Vote;
import ru.shasoft.votingsystem.vote.object.VoteCount;

import java.time.LocalDate;

import static ru.shasoft.votingsystem.menu.MenuTestData.date2024_11_05;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_2;
import static ru.shasoft.votingsystem.user.UserTestData.ADMIN_ID;
import static ru.shasoft.votingsystem.user.UserTestData.USER_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class);
    public static final MatcherFactory.Matcher<VoteCount> VOTE_COUNT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteCount.class);

    public static final int VOTE_ID_1 = 1;
    public static final int VOTE_ID_2 = 2;
    public static final int VOTE_ID_3 = 3;
    public static final int NOT_FOUND = 100;

    public static final Vote vote1 = new Vote(VOTE_ID_1, date2024_11_05, USER_ID, RESTAURANT_ID_1);
    public static final Vote vote2 = new Vote(VOTE_ID_2, date2024_11_05, ADMIN_ID, RESTAURANT_ID_1);
    public static final Vote vote3 = new Vote(VOTE_ID_3, LocalDate.now(), ADMIN_ID, RESTAURANT_ID_2);

    public static final VoteCount voteCountForRestaurant1 = new VoteCount(date2024_11_05, RESTAURANT_ID_1, 2L);
    public static final VoteCount voteCountForRestaurant2 = new VoteCount(LocalDate.now(), RESTAURANT_ID_2, 1L);
}
