package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL_1 =
            new Meal(100002, LocalDateTime.of(2020, 03, 01, 10, 15),
                    "description-1(user1)", 500);
    public static final Meal MEAL_2 =
            new Meal(100003, LocalDateTime.of(2020, 03, 01, 11, 15),
                    "description-2(user1)", 600);
    public static final Meal MEAL_3 =
            new Meal(100004, LocalDateTime.of(2020, 03, 01, 12, 15),
                    "description-1(user2)", 700);

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2020, 11, 11, 11, 11, 11),
                "description-new", 999);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "skip_field1", "skip_field2");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("skip_field1", "skip_field2").isEqualTo(expected);
    }
}
