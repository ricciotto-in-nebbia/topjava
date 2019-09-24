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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        //тестовые данные перемешаны
//        List<UserMeal> mealList = Arrays.asList(
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,19,0), "Ужин", 510),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,12,12), "Обед", 1000),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,11,0), "Завтрак", 1000),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,44), "Ужин", 500),
//                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500)
//        );
//        getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(22,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        ArrayList<UserMeal> arrayMealList = new ArrayList<>(mealList);
        Map<LocalDate, Integer> dateAndCalories = new HashMap<>();

//        Comparator<UserMeal> compareByDate = new Comparator<UserMeal>() {
//            @Override
//            public int compare(UserMeal o1, UserMeal o2) {
//                return o1.getDateTime().toLocalDate().compareTo(o2.getDateTime().toLocalDate());
//            }
//        };

//        Comparator<UserMeal> compareByDate = (o1, o2) -> o1.getDateTime().toLocalDate().compareTo(o2.getDateTime().toLocalDate());

        Comparator<UserMeal> compareByDate = Comparator.comparing(UserMeal::getDateTime);
        arrayMealList.sort(compareByDate);

        for (UserMeal userMeal : arrayMealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            int calories = dateAndCalories.getOrDefault(dateTime.toLocalDate(), 0);
            dateAndCalories.put(dateTime.toLocalDate(), calories + userMeal.getCalories());
        }

        List<UserMealWithExceed> todayUserMealWithExceed = arrayMealList
                .stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map((UserMeal userMeal) -> UserMealConverter.converter(userMeal, dateAndCalories, caloriesPerDay))
                .collect(Collectors.toList());

        for (UserMealWithExceed user : todayUserMealWithExceed) {
            System.out.println(user);
        }

        return todayUserMealWithExceed;
    }
}
