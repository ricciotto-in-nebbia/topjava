package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MapMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getAllList;

@WebServlet(value = "/meals", loadOnStartup = 0)
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final long serialVersionUID = 1L;
    private static final String EDIT_MEAL = "/mealsEdit.jsp";
    private static final String LIST_USER = "/meals.jsp";
    private MealDao mealDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.debug("MealServlet / init");
        mealDao = new MapMealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet / doGet");

        String forward;
        String action = parseAction(request);
        Integer mealId = getId(request);

        switch (action) {
            case "delete":
                log.debug("MealServlet / doPost: action delete");
                mealDao.delete(mealId);
                requestAttributes(request);
                response.sendRedirect("meals");
                return;
            case "edit":
                log.debug("MealServlet / doPost: action edit");
                Meal meal = mealDao.getById(mealId);
                request.setAttribute("meal", meal);
                request.setAttribute("mealId", mealId);
                forward = EDIT_MEAL;
                break;
            default:
                requestAttributes(request);
                forward = LIST_USER;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet / doPost");

        String action = parseAction(request);
        Integer mealId = getId(request);

        switch (action) {
            case "update":
                log.debug("MealServlet / doPost: action update");
                mealDao.delete(mealId);
                addNewMeal(mealId, request);
                break;
            case "create":
                log.debug("MealServlet / doPost: action create");
                addNewMeal(mealId, request);
        }
        response.sendRedirect("meals");
    }

    private void requestAttributes(HttpServletRequest request) {
        request.setAttribute("mealTosList", getAllList(mealDao.getAll()));
    }

    private static String parseAction(final HttpServletRequest request) {
        final String action = request.getParameter("action");
        log.debug("MealsUtil.parseAction: " + action);
        if (nonNull(action) && !action.isEmpty()) {
            return action.toLowerCase();
        } else {
            return "";
        }
    }

    private Integer getId(HttpServletRequest request) {
        final String id = request.getParameter("mealId");
        return id == null || id.isEmpty() ? null : Integer.parseInt(id);
    }

    private void addNewMeal(Integer mealId, HttpServletRequest request) {
        String datetime = request.getParameter("datetime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        mealDao.add(new Meal(mealId, LocalDateTime.parse(datetime), description, Integer.parseInt(calories)));
    }
}