package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.UserTestData.*;

class AdminAjaxControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/ajax/admin/users/";

    @Autowired
    private UserService userService;

    @Test
    void changeEnabled() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "changeEnabled/" + USER_ID + "/true"))
                .andDo(print())
                .andExpect(status().isNoContent());
        User userFromDB = userService.get(USER_ID);
        USER_MATCHER.assertMatch(USER_DISABLED, userFromDB);
    }
}