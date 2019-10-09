package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealDaoImpl implements MealDao {

    private static AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Meal> mealMap;

    {
        mealMap = new ConcurrentHashMap<>();
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 24, 8, 30), "Завтрак", 250));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 24, 13, 20), "Обед", 405));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 24, 17, 0), "Полдник", 1530));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 24, 21, 45), "Ужин", 470));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 25, 9, 30), "Завтрак", 690));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 25, 14, 15), "Обед", 230));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 25, 21, 45), "Ужин", 1000));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 26, 8, 50), "Завтрак", 690));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 26, 15, 15), "Обед", 1380));
        add(new Meal(counter.getAndIncrement(), LocalDateTime.of(2019, Month.SEPTEMBER, 26, 21, 45), "Ужин", 920));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void add(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.getAndIncrement());
        }
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(Integer mealId) {
        return mealId != null ? mealMap.get(mealId) : null;
    }

    @Override
    public void delete(Integer mealId) {
        if (mealId != null) mealMap.remove(mealId);
    }
}
