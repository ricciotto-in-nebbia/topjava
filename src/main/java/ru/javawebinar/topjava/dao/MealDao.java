package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    List<Meal> getAll();

    void add(Meal meal);

    Meal getById(Integer mealId);

    void delete(Integer mealId);
}
