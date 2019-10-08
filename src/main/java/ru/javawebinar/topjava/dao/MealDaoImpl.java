package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {
    private static AtomicInteger counter = new AtomicInteger(1);

    private Map<Integer, Meal> mealMap;

    {
        mealMap = new ConcurrentHashMap<>();
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 8, 30), "Завтрак", 250));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 13, 20), "Обед", 405));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 17, 0), "Полдник", 1530));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 24, 21, 45), "Ужин", 470));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 25, 9, 30), "Завтрак", 690));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 25, 14, 15), "Обед", 230));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 25, 21, 45), "Ужин", 1000));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 26, 8, 50), "Завтрак", 690));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 26, 15, 15), "Обед", 1380));
        mealMap.put(counter.getAndIncrement(), new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 26, 21, 45), "Ужин", 920));
    }

    @Override
    public Map<Integer, Meal> getAllMeals() {
        return mealMap;
    }

    @Override
    public void addMeal(Meal meal) {
        mealMap.put(counter.getAndIncrement(), meal);
    }

    @Override
    public Meal getMealById(int mealId) {
        return mealMap.get(mealId);
    }

    @Override
    public void deleteMeal(int mealId) {
        mealMap.remove(mealId);
    }
}
