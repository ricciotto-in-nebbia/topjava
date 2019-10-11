package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    void save(Meal meal);

    Meal getById(Integer mealId);

    void deleteById(Integer mealId);

    List<Meal> getAll();
}