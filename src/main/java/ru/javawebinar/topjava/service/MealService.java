package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(int userId, Meal meal) {
        log.info("MealService create {}", meal);
        return repository.save(userId, meal);
    }

    public void delete(int userId, int id) {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(int userId, int id) {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public void update(int userId, Meal Meal) {
        checkNotFoundWithId(repository.save(userId, Meal), Meal.getId());
    }

    public List<MealTo> getAll(int userId) {
        final List<Meal> meals = repository.getAll(userId);
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFiltered(int userId, LocalDate d1, LocalDate d2, LocalTime t1, LocalTime t2) {
        final List<Meal> meals = repository.getAllFiltered(userId, d1, d2);
        return MealsUtil.filteredByStreams(meals, t1, t2, DEFAULT_CALORIES_PER_DAY);
    }
}