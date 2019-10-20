package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll(){
        int userId = SecurityUtil.authUserId();
        log.info("get all food for user {}", userId);
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int mealId){
        int userId = SecurityUtil.authUserId();
        log.info("get {} from user {}", mealId, userId);
        return service.get(mealId, userId);
    }

    public List<MealTo> getByDateAndTime(LocalDate dateFrom, LocalTime startTime, LocalDate dateTo, LocalTime endTime){
        int userId = SecurityUtil.authUserId();
        if (dateFrom == null){
            dateFrom = LocalDate.MIN;
        }
        if (dateTo == null){
            dateTo = LocalDate.MAX;
        }
        if (startTime == null){
            startTime = LocalTime.MIN;
        }
        if (endTime == null){
            endTime = LocalTime.MAX;
        }
        log.info("get meal from user {}, in data range from {} to {}", userId, dateFrom, dateTo);
        return MealsUtil.getFilteredTos(service.getByDate(dateFrom, dateTo, userId), SecurityUtil.authUserCaloriesPerDay(),
                startTime, endTime);
    }

    public Meal create(Meal meal){
        int userId = SecurityUtil.authUserId();
        log.info("create {} for user {}", meal, userId);
        return service.create(meal);
    }

    public void delete(int id){
        int userId = SecurityUtil.authUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public void update (Meal meal, int id){
        int userId = SecurityUtil.authUserId();
        log.info("update {} for user {}", meal, userId);
        if (userId != id)
            throw new IllegalArgumentException(meal + " must be with id=" + id);
        service.update(meal);
    }
}