package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;
import static java.util.Objects.nonNull;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredList;
import static ru.javawebinar.topjava.util.TimeUtil.parseTime;

@WebServlet(value = "/meals", loadOnStartup = 0)
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("doGet: redirect to MealServlet");

        @SuppressWarnings("unchecked")
        List<Meal> mealList = (List<Meal>) req.getServletContext().getAttribute("mealList");

        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        List<MealTo> mealToList;
        if (nonNull(startTime) && nonNull(endTime) && !startTime.isEmpty() && !endTime.isEmpty()) {
            mealToList = getFilteredList(mealList, parseTime(startTime), parseTime(endTime), DEFAULT_CALORIES_PER_DAY);
        } else {
            mealToList = getFilteredList(mealList, LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
        }

        req.setAttribute("mealToList", mealToList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("doPost: redirect to MealServlet");

        @SuppressWarnings("unchecked")
        List<Meal> mealList = (List<Meal>) req.getServletContext().getAttribute("mealList");

        String actionName = req.getParameter("actionMeal");
        String datetime = req.getParameter("datetime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        if (actionName.contains("Create")) {
            mealList.add(new Meal(LocalDateTime.parse(datetime), description, Integer.parseInt(calories)));
        }

        if (actionName.contains("updateJSP")) {
            List<Meal> meals = new ArrayList<>();
            String id = actionName.substring(actionName.indexOf("?") + 1);
            int mealId = -1;
            if (!id.isEmpty()) {
                mealId = Integer.parseInt(id);
            }

            int finalMealId = mealId;
            mealList.forEach(e -> {
                if (e.getId() == finalMealId) {
                    meals.add(e);
                }
            });

            req.setAttribute("meals", meals);
            req.getRequestDispatcher("/update.jsp").forward(req, resp);
        }

        if (actionName.contains("updateMeal")) {
            String id = actionName.substring(actionName.indexOf("?") + 1);
            int mealId = -1;

            if (!id.isEmpty()) {
                mealId = Integer.parseInt(id);
            }

            int finalMealId = mealId;
            mealList.forEach(e -> {
                if (e.getId() == finalMealId) {
                    mealList.remove(e);
                }
            });

            mealList.add(new Meal(LocalDateTime.parse(datetime), description, Integer.parseInt(calories)));
        }

        if (actionName.contains("deleteMeal")) {
            String id = actionName.substring(actionName.indexOf("?") + 1);
            int mealId = -1;

            if (!id.isEmpty()) {
                mealId = Integer.parseInt(id);
            }

            int finalMealId = mealId;
            mealList.forEach(e -> {
                if (e.getId() == finalMealId) {
                    mealList.remove(e);
                }
            });
        }
        doGet(req, resp);
    }
}