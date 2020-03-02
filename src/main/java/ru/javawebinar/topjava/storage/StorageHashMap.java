package ru.javawebinar.topjava.storage;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class StorageHashMap implements Storage {

    private Map<Integer, Meal> storageMeal = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void update(Meal meal) {
        storageMeal.merge(meal.getUuid(), meal, (meal1, meal2) -> meal2);
    }

    @Override
    public void save(Meal meal) {
        meal.setUuid(counter.incrementAndGet());
        storageMeal.put(meal.getUuid(), meal);
    }

    @Override
    public Meal get(int uuid) {
        return storageMeal.get(uuid);
    }

    @Override
    public void delete(int uuid) {
        storageMeal.remove(uuid);
    }

    @Override
    public Collection<Meal> getAll() {
        return storageMeal.values();
    }

    @Override
    public int size() {
        return storageMeal.size();
    }

    @Override
    public List<MealTo> getAllMealTo() {
        List<Meal> meals = new ArrayList<>(storageMeal.values());
        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        return mealsTo;
    }
}
