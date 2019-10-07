package ru.javawebinar.topjava.dao.Impl;

import ru.javawebinar.topjava.dao.ListDatabase;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListDatabaseImpl implements ListDatabase {
    private static AtomicInteger counter = new AtomicInteger(1);

    private List<Meal> mealList = new CopyOnWriteArrayList<>(List.of(
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 8, 30), "Завтрак", 250),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 13, 20), "Обед", 405),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 17, 0), "Полдник", 1530),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 21, 45), "Ужин", 470),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 25, 9, 30), "Завтрак", 690),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 25, 14, 15), "Обед", 230),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 25, 21, 45), "Ужин", 1000),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 26, 8, 50), "Завтрак", 690),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 26, 15, 15), "Обед", 1380),
            new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 26, 21, 45), "Ужин", 920)
    ));

    @Override
    public List<Meal> getMealList() {
        return mealList;
    }

    public static int getId() {
        return counter.getAndIncrement();
    }
}
