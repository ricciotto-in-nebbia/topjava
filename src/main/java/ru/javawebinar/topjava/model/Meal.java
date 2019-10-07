package ru.javawebinar.topjava.model;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.Impl.ListDatabaseImpl;
import org.slf4j.Logger;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class Meal {
    private static final Logger log = getLogger(Meal.class);
    int id;
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        log.debug("Meal: create a new object");

        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = ListDatabaseImpl.getId();
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getId() {
        return id;
    }
}
