package ru.javawebinar.topjava.model;

import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class Meal {
    private static final Logger log = getLogger(Meal.class);
    private Integer id;
    private LocalDateTime dateTime;
    private String description;
    private int calories;

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        log.debug("Meal: create a new object");
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
