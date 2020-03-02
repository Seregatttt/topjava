package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;
import java.util.List;

public interface Storage {

    void update(Meal meal);

    void save(Meal meal);

    Meal get(int uuid);

    void delete(int uuid);

    Collection<Meal> getAll();

    List<MealTo> getAllMealTo();

    int size();

}
