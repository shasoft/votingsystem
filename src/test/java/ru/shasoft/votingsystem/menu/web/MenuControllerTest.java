package ru.shasoft.votingsystem.menu.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shasoft.votingsystem.AbstractControllerTest;
import ru.shasoft.votingsystem.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shasoft.votingsystem.menu.MenuTestData.*;
import static ru.shasoft.votingsystem.menu.web.MenuController.REST_URL;
import static ru.shasoft.votingsystem.user.UserTestData.USER_MAIL;

class MenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void byDate() throws Exception {
        ResultActions action = perform(
                MockMvcRequestBuilders.get(
                        REST_URL_SLASH + "by-date?date=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(MENU_MATCHER.contentJson(menu3, menu4));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void byRestaurant() throws Exception {
        ResultActions action = perform(
                MockMvcRequestBuilders.get(
                        REST_URL_SLASH + "by-restaurant?restaurantId=" + RestaurantTestData.RESTAURANT_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(MENU_MATCHER.contentJson(menu2, menu1));
    }
}