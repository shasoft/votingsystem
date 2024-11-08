package ru.shasoft.votingsystem.vote.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.shasoft.votingsystem.AbstractControllerTest;
import ru.shasoft.votingsystem.common.util.JsonUtil;
import ru.shasoft.votingsystem.vote.model.Vote;
import ru.shasoft.votingsystem.vote.repository.VoteRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.shasoft.votingsystem.user.UserTestData.ADMIN_MAIL;
import static ru.shasoft.votingsystem.vote.VoteTestData.*;
import static ru.shasoft.votingsystem.vote.web.VoteAdminController.REST_URL;

class AdminVoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void like() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(newId), newVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void unlike() throws Exception {
        assertTrue(repository.findById(VOTE_ID_1).isPresent());
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "unlike")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote1)))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(VOTE_ID_1).isEmpty());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void checkLike() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "value")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void checkNotLike() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + "value")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(voteNotLike)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"));
    }
}