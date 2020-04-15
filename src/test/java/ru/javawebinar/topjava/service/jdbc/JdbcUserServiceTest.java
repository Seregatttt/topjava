package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    private static final Logger log = LoggerFactory.getLogger(JdbcUserServiceTest.class);

    @Test
    public void testCash() throws Exception {
        log.warn("start testCash   ");
        List<User> all = service.getAll();
        log.warn("all.size()=   "+all.size());
        assertEquals(2, all.size());
        service.delete(USER_ID);
        List<User> all2 = service.getAll();
        log.warn("all2.size()=   "+all2.size());
        assertEquals(2, all2.size());
       // USER_MATCHER.assertMatch(all2, ADMIN, USER);
    }
}