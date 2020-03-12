package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    List<Meal> getAll();

    List<Meal> getAllFiltered(LocalDate d1, LocalDate d2, LocalTime t1, LocalTime t2);
}
