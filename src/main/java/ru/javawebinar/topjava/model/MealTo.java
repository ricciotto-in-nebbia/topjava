package ru.javawebinar.topjava.model;

import org.slf4j.Logger;

import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealTo {
    private static final Logger log = getLogger(MealTo.class);
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final boolean excess;
    int id;


    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess, int id) {
        log.debug("MealTo: create a new object");

        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id = id;
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

    public int getId() {
        return id;
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