package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {

    private  int uuid;

    private  LocalDateTime dateTime;

    private  String description;

    private  int calories;

    private  boolean excess;

    public MealTo( LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public MealTo(int uuid, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this(dateTime,description,calories,excess);
        this.uuid = uuid;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
