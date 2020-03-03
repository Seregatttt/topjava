package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
    public List<Meal> getAll() {
        return new ArrayList<>(storageMeal.values());
    }
}
