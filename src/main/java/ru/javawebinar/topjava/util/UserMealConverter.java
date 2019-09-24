package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class UserMealConverter {
    public static UserMealWithExceed converter(UserMeal userMeal, Map<LocalDate, Integer> dateAndCalories, int caloriesPerDay){
        LocalDateTime dateTime = userMeal.getDateTime();
        String description = userMeal.getDescription();
        int calories = userMeal.getCalories();
        boolean exceed = dateAndCalories.get(dateTime.toLocalDate()) > caloriesPerDay;
        return new UserMealWithExceed(dateTime, description, calories, exceed);
    }
}
