package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = getLogger(MealServiceTest.class);

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertEquals(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void update() throws Exception {
        Meal meal = service.get(1, USER_ID);
        meal.setDescription("update discription");
        service.update(meal, USER_ID);
        assertEquals(service.get(1, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(3, ADMIN_ID);
        service.get(3, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(99, ADMIN_ID);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(3, ADMIN_ID);
        log.debug("get() = " + meal);
        assertEquals(meal, MEAL_3);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(99, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundUser() throws Exception {
        service.get(99, 99);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertEquals(2, all.size());
        assertEquals(Arrays.asList(MEAL_2, MEAL_1), all);
    }
}