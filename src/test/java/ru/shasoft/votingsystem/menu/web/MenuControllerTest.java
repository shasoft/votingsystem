package ru.shasoft.votingsystem.menu.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shasoft.votingsystem.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shasoft.votingsystem.menu.MenuTestData.*;
import static ru.shasoft.votingsystem.menu.web.MenuController.REST_URL;
import static ru.shasoft.votingsystem.user.UserTestData.USER_MAIL;

class MenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Test
    @WithUserDetails(value = USER_MAIL)
    void today() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(MenuController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(MENU_MATCHER.contentJson(menu3, menu4));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void weekly() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "weekly"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        action.andExpect(MENU_MATCHER.contentJson(menu3, menu4));
    }
}