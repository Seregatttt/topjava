package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(InMemoryMealRepository.this::save);
    }

    @Override
    public Meal save(Meal meal) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(SecurityUtil.authUserId(), b -> new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
        repository.put(SecurityUtil.authUserId(), meals);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(SecurityUtil.authUserId())) {
            return repository.get(SecurityUtil.authUserId()).remove(id) != null;
        }
        log.info("ERROR delete id={}", id);
        return false;
    }

    @Override
    public Meal get(int id) {
        if (repository.containsKey(SecurityUtil.authUserId())) {
            return repository.get(SecurityUtil.authUserId()) == null ? null : repository.get(SecurityUtil.authUserId()).get(id);
        }
        log.info("ERROR get id={}", id);
        return null;
    }

    @Override
    public List<Meal> getAll() {
        if (repository.containsKey(SecurityUtil.authUserId())) {
            return repository.get(SecurityUtil.authUserId()).values()
                    .stream()
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Meal> getAllFiltered(LocalDate d1, LocalDate d2, LocalTime t1, LocalTime t2) {
        if (repository.containsKey(SecurityUtil.authUserId())) {
            return repository.get(SecurityUtil.authUserId()).values()
                    .stream()
                    .filter(x -> x.getDate().compareTo(d1) >= 0 && x.getDate().compareTo(d2) <= 0
                            && x.getTime().compareTo(t1) >= 0 && x.getTime().compareTo(t2) <= 0)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}

