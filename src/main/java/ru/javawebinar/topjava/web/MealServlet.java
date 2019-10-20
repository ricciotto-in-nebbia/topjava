package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        if (id != null) {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")), SecurityUtil.authUserId());

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew())
                mealRestController.create(meal);
            else
                mealRestController.update(meal, SecurityUtil.authUserId());
            response.sendRedirect("meals");
        } else {
            String dateFromStr = request.getParameter("date_from");
            String dateToStr = request.getParameter("date_to");

            String timeFromStr = request.getParameter("time_from");
            String timeToStr = request.getParameter("time_to");

            LocalDate dateFrom = null;
            LocalDate dateTo = null;
            LocalTime timeFrom = null;
            LocalTime timeTo = null;

            if (dateFromStr != null && !dateFromStr.isEmpty()) {
                try {
                    dateFrom = LocalDate.parse(dateFromStr);
                } catch (DateTimeParseException ignore) {
                }
            }
            if (dateToStr != null && !dateToStr.isEmpty()) {
                try {
                    dateTo = LocalDate.parse(dateToStr);
                } catch (DateTimeParseException ignore) {
                }
            }
            if (timeFromStr != null && !timeFromStr.isEmpty()) {
                try {
                    timeFrom = LocalTime.parse(timeFromStr);
                } catch (DateTimeParseException ignore) {
                }
            }
            if (timeToStr != null && !timeToStr.isEmpty()) {
                try {
                    timeTo = LocalTime.parse(timeToStr);
                } catch (DateTimeParseException ignore) {
                }
            }
            request.setAttribute("dateFrom", dateFrom);
            request.setAttribute("dateTo", dateTo);
            request.setAttribute("timeFrom", timeFrom);
            request.setAttribute("timeTo", timeTo);

            log.info("getByDateAndTime");
            request.setAttribute("meals", mealRestController.getByDateAndTime(dateFrom, timeFrom, dateTo, timeTo));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (appCtx != null)
            appCtx.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId()) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}