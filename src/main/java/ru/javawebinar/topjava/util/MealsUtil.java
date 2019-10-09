package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsUtil {
    private static final Logger log = getLogger(MealsUtil.class);
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getAllList(List<Meal> mealList) {
        log.debug("MealsUtil: getMealTosMap");

        Map<LocalDate, Integer> dateAndCalories = mealList.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(Meal::getCalories)));

        return mealList.stream()
                .map(meal -> createTo(meal, dateAndCalories.get(meal.getDateTime().toLocalDate()) > DEFAULT_CALORIES_PER_DAY))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}