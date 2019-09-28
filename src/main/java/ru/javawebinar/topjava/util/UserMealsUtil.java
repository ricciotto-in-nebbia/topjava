package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateAndCalories = mealList
                .stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList
                .stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map((UserMeal userMeal) -> convert(userMeal, dateAndCalories, caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded_2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateAndCalories = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            int calories = dateAndCalories.getOrDefault(dateTime.toLocalDate(), 0);
            dateAndCalories.put(dateTime.toLocalDate(), calories + userMeal.getCalories());
        }

        List<UserMealWithExceed> todayUserMealWithExceed = new ArrayList<>();

        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                todayUserMealWithExceed.add(convert(userMeal, dateAndCalories, caloriesPerDay));
            }
        }
        return todayUserMealWithExceed;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded_3(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateAndCalories = new HashMap<>();

        mealList.forEach(userMeal -> dateAndCalories.put(userMeal.getDateTime().toLocalDate(),
                dateAndCalories.getOrDefault(userMeal.getDateTime().toLocalDate(), 0)
                        + userMeal.getCalories()));

        List<UserMealWithExceed> todayUserMealWithExceed = new ArrayList<>();

        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                todayUserMealWithExceed.add(convert(userMeal, dateAndCalories, caloriesPerDay));
            }
        });

        return todayUserMealWithExceed;
    }

    private static UserMealWithExceed convert(UserMeal userMeal, Map<LocalDate, Integer> dateAndCalories, int caloriesPerDay) {
        LocalDateTime dateTime = userMeal.getDateTime();
        String description = userMeal.getDescription();
        int calories = userMeal.getCalories();
        boolean exceed = dateAndCalories.get(dateTime.toLocalDate()) > caloriesPerDay;
        return new UserMealWithExceed(dateTime, description, calories, exceed);
    }
}