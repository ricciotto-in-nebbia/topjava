package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.Impl.ListDatabaseImpl;
import ru.javawebinar.topjava.dao.ListDatabase;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        ListDatabase listDatabase = new ListDatabaseImpl();

        servletContext.setAttribute("mealList", listDatabase.getMealList());
    }
}
