package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> todayUserMealWithExceed = new ArrayList<>();
        Map<LocalDate, Integer> dateAndCalories = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            int calories = dateAndCalories.getOrDefault(dateTime.toLocalDate(), 0);
            dateAndCalories.put(dateTime.toLocalDate(), calories + userMeal.getCalories());
        }

        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                String description = userMeal.getDescription();
                int calories = userMeal.getCalories();
                boolean exceed = dateAndCalories.get(dateTime.toLocalDate()) > caloriesPerDay;
//                System.out.println("dateTime: " + dateTime + " description: " + description + " calories: " + calories + " exceed: " + exceed);
                todayUserMealWithExceed.add(new UserMealWithExceed(dateTime, description, calories, exceed));
            }
        }

//        for (Map.Entry<LocalDate, Integer> map : dateAndCalories.entrySet()) {
//            System.out.println(map.getKey() + " " + map.getValue());
//        }
        return todayUserMealWithExceed;
    }
}
