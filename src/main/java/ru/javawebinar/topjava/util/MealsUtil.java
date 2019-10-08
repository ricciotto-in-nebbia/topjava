package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;

public class MealsUtil {
    private static final Logger log = getLogger(MealsUtil.class);
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static Map<Integer, MealTo> getMealTosMap(Map<Integer, Meal> mealMap, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        log.debug("MealsUtil: getMealTosMap");

        Map<LocalDate, Integer> dateAndCalories = mealMap.values().stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(Meal::getCalories)));

        return mealMap.entrySet().stream()
                .filter(e -> TimeUtil.isBetween(e.getValue().getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toMap((Map.Entry::getKey), v -> createTo(v.getValue(), dateAndCalories.get(v.getValue().getDateTime().toLocalDate()) > caloriesPerDay)));
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static String parseAction(final HttpServletRequest request) {
        final String action = request.getParameter("action");
        log.debug("MealsUtil.parseAction: " + action);

        if (nonNull(action) && !action.isEmpty()) {
            return action.toLowerCase();
        } else {
            return "actionIsEmpty";
        }
    }
}