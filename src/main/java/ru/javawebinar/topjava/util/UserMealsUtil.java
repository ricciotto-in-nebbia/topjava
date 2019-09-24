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

        //тестовые данные перемешаны
//        List<UserMeal> mealList = Arrays.asList(
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500)
//        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(22,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        ArrayList<UserMeal> list = new ArrayList<>(mealList);
        List<UserMealWithExceed> todayUserMealWithExceed = new ArrayList<>();
        Map<LocalDate, Integer> dateAndCalories = new HashMap<>();

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getDateTime().toLocalDate().isAfter(list.get(j).getDateTime().toLocalDate())) {
                    UserMeal tmp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, tmp);
                }
            }
        }

        for (UserMeal userMeal : list) {
            LocalDateTime dateTime = userMeal.getDateTime();
            int calories = dateAndCalories.getOrDefault(dateTime.toLocalDate(), 0);
            dateAndCalories.put(dateTime.toLocalDate(), calories + userMeal.getCalories());
        }

        for (UserMeal userMeal : list) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                String description = userMeal.getDescription();
                int calories = userMeal.getCalories();
                boolean exceed = dateAndCalories.get(dateTime.toLocalDate()) > caloriesPerDay;
                System.out.println("dateTime: " + dateTime + " description: " + description + " calories: " + calories + " exceed: " + exceed);
                todayUserMealWithExceed.add(new UserMealWithExceed(dateTime, description, calories, exceed));
            }
        }

//        for (Map.Entry<LocalDate, Integer> map : dateAndCalories.entrySet()) {
//            System.out.println(map.getKey() + " " + map.getValue());
//        }
        return todayUserMealWithExceed;
    }

    }
