package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsUtil {
    private static final Logger log = getLogger(MealsUtil.class);
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getFilteredList(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        log.debug("MealsUtil: getFilteredList");

        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return mealList.stream().filter(e -> TimeUtil.isBetween(e.getTime(), startTime, endTime))
                .map(e -> createTo(e, caloriesSumByDate.get(e.getDate()) > caloriesPerDay))
                .sorted(Comparator.comparing(MealTo::getDateTime))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess, meal.getId());
    }
}