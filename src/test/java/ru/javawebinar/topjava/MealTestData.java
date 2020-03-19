package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal MEAL_1 =
            new Meal(1, LocalDateTime.of(2020, 03, 01, 10, 15),
                    "description-1(user1)", 500);
    public static final Meal MEAL_2 =
            new Meal(2, LocalDateTime.of(2020, 03, 01, 11, 15),
                    "description-2(user1)", 600);
    public static final Meal MEAL_3 =
            new Meal(3, LocalDateTime.of(2020, 03, 01, 12, 15),
                    "description-1(user2)", 700);
    public static final Meal MEAL_4 =
            new Meal(4, LocalDateTime.of(2020, 03, 01, 13, 15),
                    "description-2(user2)", 800);

    public static Meal getNew() {
        return new Meal(2, LocalDateTime.of(2020, 11, 11, 11, 11, 11),
                "description-new", 999);
    }
}
