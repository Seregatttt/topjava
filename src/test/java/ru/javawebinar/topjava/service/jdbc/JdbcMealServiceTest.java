package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.JdbcBeanValidateApi;
import ru.javawebinar.topjava.util.exception.ValidationException;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Test(expected = ValidationException.class)
    public void createMealNotNullUser() {
        Meal newMeal = service.create(new Meal(null, of(2020, Month.JUNE, 1, 18, 0),
                "супер ужин", 999), USER_ID);
        //newMeal.setUser(USER); create without user
        //Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(newMeal));
    }

    @Test(expected = ValidationException.class)
    public void createMealNotBlankDescription() {
        Meal newMeal = service.create(new Meal(null, of(2020, Month.JUNE, 1, 18, 0), "  ", 999), USER_ID);
        newMeal.setUser(USER);
        //Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(newMeal));
    }

    @Test(expected = ValidationException.class)
    public void createMealNotRangeCallories() {
        Meal newMeal = service.create(new Meal(null, of(2020, Month.JUNE, 1, 18, 0), "супер ужин", 1111111), USER_ID);
        newMeal.setUser(USER);
        //Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(newMeal));
    }
}