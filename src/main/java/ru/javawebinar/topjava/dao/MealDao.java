package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface MealDao {

    Map<Integer, Meal> getAllMeals();

    void addMeal(Meal meal);

    Meal getMealById(int mealId);

    void deleteMeal(int mealId);
}
