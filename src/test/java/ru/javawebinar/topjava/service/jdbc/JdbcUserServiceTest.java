package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.JdbcBeanValidateApi;
import ru.javawebinar.topjava.util.exception.ValidationException;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Test(expected = ValidationException.class)
    public void createNotBlankNameUser() {
        User created = service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.ROLE_USER));
       // JdbcBeanValidateApi.validate(created);
       // Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(created));
    }

    @Test(expected = ValidationException.class)
    public void createNotBlankEmailUser() {
        User created = service.create(new User(null, "User", "  ", "password", Role.ROLE_USER));
       // Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(created));
    }

    @Test(expected = ValidationException.class)
    public void createNotFormatEmailUser() {
        User created = service.create(new User(null, "User", "xxx", "password", Role.ROLE_USER));
        //Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(created));
    }


    @Test(expected = ValidationException.class)
    public void createNotBlankPassUser() {
        User created = service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.ROLE_USER));
       // Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(created));
    }

    @Test(expected = ValidationException.class)
    public void createNotRangePassUser() {
        User created = service.create(new User(null, "User", "mail@yandex.ru", "555", Role.ROLE_USER));
       // Assert.assertThrows(ValidationException.class, () -> JdbcBeanValidateApi.validate(created));
    }
}