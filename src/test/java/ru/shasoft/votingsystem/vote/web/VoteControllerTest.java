package ru.shasoft.votingsystem.vote.web;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shasoft.votingsystem.AbstractControllerTest;
import ru.shasoft.votingsystem.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shasoft.votingsystem.common.validation.ValidationUtil.VOTE_END_TIME;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.*;
import static ru.shasoft.votingsystem.user.UserTestData.*;
import static ru.shasoft.votingsystem.vote.web.VoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';
    private static final LocalTime timeBefore = VOTE_END_TIME.minusHours(1);
    private static final LocalTime timeAfter = VOTE_END_TIME.plusHours(1);

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void like() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeBefore);
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + restaurantId))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void likeAfter() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeAfter);
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + restaurantId))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isEmpty());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void unlike() throws Exception {
        final int restaurantId = RESTAURANT_ID_3;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeBefore);
            perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + restaurantId))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isEmpty());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void checkLike() throws Exception {
        final int restaurantId = RESTAURANT_ID_2;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isPresent());
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + restaurantId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void checkNotLike() throws Exception {
        final int restaurantId = RESTAURANT_ID_1;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), USER_ID).isEmpty());
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + restaurantId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void likeGuest() throws Exception {
        final int restaurantId = RESTAURANT_ID_1;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), GUEST_ID).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeBefore);
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + restaurantId))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), GUEST_ID).isEmpty());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void likeAdmin() throws Exception {
        final int restaurantId = RESTAURANT_ID_1;
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), ADMIN_ID).isEmpty());
        try (MockedStatic<LocalTime> mock = mockStatic(LocalTime.class, CALLS_REAL_METHODS)) {
            mock.when(LocalTime::now).thenReturn(timeBefore);
            perform(MockMvcRequestBuilders.put(REST_URL_SLASH + restaurantId))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
        assertTrue(repository.findVote(restaurantId, LocalDate.now(), ADMIN_ID).isPresent());
    }
}