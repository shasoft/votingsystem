package ru.shasoft.votingsystem.vote.web;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shasoft.votingsystem.AbstractControllerTest;
import ru.shasoft.votingsystem.vote.model.Vote;
import ru.shasoft.votingsystem.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shasoft.votingsystem.common.validation.ValidationUtil.VOTE_END_TIME;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.*;
import static ru.shasoft.votingsystem.user.UserTestData.*;
import static ru.shasoft.votingsystem.vote.VoteTestData.*;
import static ru.shasoft.votingsystem.vote.web.VoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final LocalTime timeBefore = VOTE_END_TIME.minusHours(1);
    private static final LocalTime timeAfter = VOTE_END_TIME.plusHours(1);
    private static final LocalDate nowDate = LocalDate.now();

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteCreate() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        final int userId = USER_ID;
        assertTrue(repository.findVote(nowDate, userId).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeBefore);
            perform(MockMvcRequestBuilders.get(REST_URL + "/vote?restaurantId=" + restaurantId))
                    .andExpect(status().isNoContent());
        }
        final Optional<Vote> optVoteAfter = repository.findVote(nowDate, userId);
        assertTrue(optVoteAfter.isPresent());
        assertEquals(optVoteAfter.get().getRestaurantId(), restaurantId);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteCreateAfter() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        final int userId = USER_ID;
        assertTrue(repository.findVote(nowDate, userId).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeAfter);
            perform(MockMvcRequestBuilders.get(REST_URL + "/vote?restaurantId=" + restaurantId))
                    .andExpect(status().isNoContent());
        }
        final Optional<Vote> optVoteAfter = repository.findVote(nowDate, userId);
        assertTrue(optVoteAfter.isPresent());
        assertEquals(optVoteAfter.get().getRestaurantId(), restaurantId);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void voteUpdate() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        final int userId = ADMIN_ID;
        final Optional<Vote> optVoteBefore = repository.findVote(nowDate, userId);
        assertTrue(optVoteBefore.isPresent());
        assertEquals(optVoteBefore.get().getRestaurantId(), RESTAURANT_ID_2);
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeBefore);
            perform(MockMvcRequestBuilders.get(REST_URL + "/vote?restaurantId=" + restaurantId))
                    .andExpect(status().isNoContent());
        }
        final Optional<Vote> optVoteAfter = repository.findVote(nowDate, userId);
        assertTrue(optVoteAfter.isPresent());
        assertEquals(optVoteAfter.get().getRestaurantId(), restaurantId);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void voteUpdateAfter() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        final int userId = ADMIN_ID;
        final Optional<Vote> optVoteBefore = repository.findVote(nowDate, userId);
        assertTrue(optVoteBefore.isPresent());
        assertEquals(optVoteBefore.get().getRestaurantId(), RESTAURANT_ID_2);
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeAfter);
            perform(MockMvcRequestBuilders.get(REST_URL + "/vote?restaurantId=" + restaurantId))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByUser() throws Exception {
        ResultActions action = perform(
                MockMvcRequestBuilders.get(
                        REST_URL_SLASH + "votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(VOTE_MATCHER.contentJson(List.of(vote1)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByAdmin() throws Exception {
        ResultActions action = perform(
                MockMvcRequestBuilders.get(
                        REST_URL_SLASH + "votes?limit=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(VOTE_MATCHER.contentJson(List.of(vote3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void byRestaurant() throws Exception {
        ResultActions action = perform(
                MockMvcRequestBuilders.get(
                        REST_URL_SLASH + "votes/by-restaurant?restaurantId=" + RESTAURANT_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(VOTE_COUNT_MATCHER.contentJson(voteCountForRestaurant2, voteCountForRestaurant1));
    }
}