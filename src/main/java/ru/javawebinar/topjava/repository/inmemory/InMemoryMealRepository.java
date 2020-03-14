package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> InMemoryMealRepository.this.save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, b -> new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null ? mealMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAll(userId, (x -> true));
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate d1, LocalDate d2) {
        return getAll(userId, (x -> x.getDate().compareTo(d1) >= 0 && x.getDate().compareTo(d2) <= 0));
    }

    public List<Meal> getAll(int userId, Predicate<Meal> filterMeal) {
        if (repository.containsKey(userId)) {
            return repository.get(userId).values()
                    .stream()
                    .filter(filterMeal)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}

