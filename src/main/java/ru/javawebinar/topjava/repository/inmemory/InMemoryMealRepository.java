package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> userMealRepository = new ConcurrentHashMap<>();
    private AtomicInteger nextIdValue = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(nextIdValue.incrementAndGet());
            if (!userMealRepository.containsKey(meal.getUserId()))
                userMealRepository.put(meal.getUserId(), new HashMap<>());
            userMealRepository.get(meal.getUserId()).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return userMealRepository.get(meal.getUserId()).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return userMealRepository.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return userMealRepository.get(userId).get(id);
    }

    @Override
    public List<Meal> getByDate(LocalDate dateFrom, LocalDate dateTo, int userId) {
        return getAll(userId).stream().filter(el -> DateTimeUtil.isBetweenDate(el.getDate(), dateFrom, dateTo)).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meal;
        if ((meal = userMealRepository.get(userId)) == null) return Collections.emptyList();
        return meal.values().stream().sorted(Comparator.comparing(Meal::getDateTime,Comparator.reverseOrder())).collect(Collectors.toList());
    }
}
