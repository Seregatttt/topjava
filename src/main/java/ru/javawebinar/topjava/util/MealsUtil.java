package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(null, 1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(null, 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(null, 1, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(null, 2, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(null, 2, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(null, 2, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(null, 2, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    // List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    //  mealsTo.forEach(System.out::println);
    // }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> filteredByStreamsDT(List<Meal> meals, String startDate, String startTime,
                                                   String endDate, String endTime, int caloriesPerDay) {
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate d1 = startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate, f1);
        LocalDate d2 = endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate, f1);
        LocalTime t1 = startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime, f2);
        LocalTime t2 = endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime, f2);

        LocalDateTime ldt1 = LocalDateTime.of(d1.getYear(), d1.getMonth(), d1.getDayOfMonth(), t1.getHour(), t1.getMinute());
        LocalDateTime ldt2 = LocalDateTime.of(d2.getYear(), d2.getMonth(), d2.getDayOfMonth(), t2.getHour(), t2.getMinute());

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenInclusiveDT(meal.getDateTime(), ldt1, ldt2))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
