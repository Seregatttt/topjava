package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
        Map<LocalDate, Integer> mapDayCalories = new HashMap<>();

        loopForMeals(meals, startTime, endTime,
                userMeal -> mapDayCalories.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (a, b) -> a + b));

        ArrayList<UserMealWithExcess> listWithExcess = new ArrayList<>();

        loopForMeals(meals, startTime, endTime,
                userMeal -> listWithExcess.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        mapDayCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)));
        return listWithExcess;
    }

    private static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapDayCalories = meals.stream()
                .filter(x -> isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .collect(HashMap::new,
                        (map, item) -> map.merge(item.getDateTime().toLocalDate(), item.getCalories(), (a, b) -> a + b),
                        (a, b) -> {
                        });

        return meals.stream()
                .filter(x -> isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .collect(
                        ArrayList::new,
                        (list, item) -> list.add(new UserMealWithExcess(item.getDateTime(), item.getDescription(), item.getCalories(),
                                mapDayCalories.get(item.getDateTime().toLocalDate()) > caloriesPerDay)),
                        (a, b) -> {
                        });
    }

    @FunctionalInterface
    interface UserMealExecute<T> {
        void doExecute(T t);
    }

    private static <T> void loopForMeals(Collection<T> collection, LocalTime startTime, LocalTime endTime, UserMealExecute<T> um) {
        for (T item : collection) {
            if (isBetweenHalfOpen(((UserMeal) item).getDateTime().toLocalTime(), startTime, endTime)) {
                um.doExecute(item);
            }
        }
    }
}
