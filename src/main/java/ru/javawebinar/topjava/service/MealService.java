package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public Meal create(Meal meal) {
        // if (userId != meal.getId()) - throw illegal operation exception
        return repository.save(meal);
    }

    public void delete(int id, int userId) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return ValidationUtil.checkNotFound(repository.getAll(userId), "User meal not found");
    }

    public List<Meal> getByDate(LocalDate dateFrom, LocalDate dateTo, int userId){
        return ValidationUtil.checkNotFound(repository.getByDate(dateFrom, dateTo, userId), "no meal found");
    }

    public void update(Meal meal) throws NotFoundException {
        ValidationUtil.checkNotFound(repository.save(meal), "can't find meal to update");
    }
}