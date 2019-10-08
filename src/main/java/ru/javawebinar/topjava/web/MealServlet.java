package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

@WebServlet(value = "/meals", loadOnStartup = 0)
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final long serialVersionUID = 1L;
    private static final String INSERT_OR_EDIT = "/mealsEdit.jsp";
    private static final String LIST_USER = "/meals.jsp";
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        log.debug("MealServlet / init: redirect to MealServlet");
        mealDao = new MealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet / doGet: redirect to MealServlet");

        String forward = "";
        String action = parseAction(request);
        int mealId;

        switch (action) {
            case "delete":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                mealDao.deleteMeal(mealId);
                requestAttributes(request);
                response.sendRedirect("meals");
                return;
            case "edit":
                forward = INSERT_OR_EDIT;
                mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealDao.getMealById(mealId);
                request.setAttribute("meal", meal);
                request.setAttribute("mealId", mealId);
                break;
            case "listMeal":
                forward = LIST_USER;
                requestAttributes(request);
                break;
            case "actionIsEmpty":
                requestAttributes(request);
                forward = LIST_USER;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet / doPost: redirect to MealServlet");

        String action = parseAction(request);
        int mealId;
        String datetime = request.getParameter("datetime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        switch (action) {
            case "update":
                log.debug("MealServlet / doPost: action update");

                mealId = Integer.parseInt(request.getParameter("mealId"));
                mealDao.deleteMeal(mealId);
                mealDao.addMeal(new Meal(LocalDateTime.parse(datetime), description, Integer.parseInt(calories)));
                break;
            case "create":
                log.debug("MealServlet / doPost: action create");
                mealDao.addMeal(new Meal(LocalDateTime.parse(datetime), description, Integer.parseInt(calories)));
        }
        response.sendRedirect("meals");
    }

    private void requestAttributes(HttpServletRequest request){
        request.setAttribute("mealTosMap", getMealTosMap(mealDao.getAllMeals(),
                LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY));
    }
}