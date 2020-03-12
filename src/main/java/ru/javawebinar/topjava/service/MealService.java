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
import java.time.format.DateTimeFormatter;
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

    public Meal create(Meal meal) {
        log.info("MealService create {}", meal);
        return repository.save(meal);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public void update(Meal Meal) {
        checkNotFoundWithId(repository.save(Meal), Meal.getId());
    }

    public List<MealTo> getAll() {
        final List<Meal> meals = repository.getAll();
        return MealsUtil.getListMealTo(meals, DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFiltered(String startDate, String startTime, String endDate, String endTime) {

        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate d1 = startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate, f1);
        LocalDate d2 = endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate, f1);
        LocalTime t1 = startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime, f2);
        LocalTime t2 = endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime, f2);

        final List<Meal> meals = repository.getAllFiltered(d1, d2, t1, t2);
        return MealsUtil.getListMealTo(meals, DEFAULT_CALORIES_PER_DAY);
    }
}