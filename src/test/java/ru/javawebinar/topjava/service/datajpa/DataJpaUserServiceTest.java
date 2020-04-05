package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getUserWithMeals() throws Exception {
        User user = service.getUserWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        MEAL_MATCHER.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void getUserWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.getUserWithMeals(1));
    }
}
