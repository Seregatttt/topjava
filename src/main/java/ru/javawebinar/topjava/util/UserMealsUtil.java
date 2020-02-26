package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 800),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess>
                mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(15, 0), 1300);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(15, 0), 1300));
    }

    private static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDateTime, UserMealWithExcess> map = new HashMap<>();
        Map<LocalDate, Integer> mapCalories = new HashMap<>();
        for (UserMeal userMeal : meals) {
            if (isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mapCalories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (a, b) -> a + b);
                map.put(userMeal.getDateTime(), new UserMealWithExcess(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(), false));
            }
        }
        System.out.println("mapCalories=" + mapCalories);

        for (Map.Entry<LocalDateTime, UserMealWithExcess> item : map.entrySet()) {
            if (mapCalories.get(item.getValue().getDateTime().toLocalDate()) > caloriesPerDay) {
                item.getValue().setExcess(true);
            }
        }
        return new ArrayList<>(map.values());
    }

    private static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapCalories = new HashMap<>();
        List<UserMealWithExcess> list = meals.stream()
                .filter(x -> isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .peek(userMeal -> mapCalories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (a, b) -> a + b))
                .map(x -> new UserMealWithExcess(x.getDateTime(),
                        x.getDescription(), x.getCalories(), false))
                .collect(Collectors.toList());

        for (UserMealWithExcess item : list) {
            if (mapCalories.get(item.getDateTime().toLocalDate()) > caloriesPerDay) {
                item.setExcess(true);
            }
        }
        return list;
    }
}
