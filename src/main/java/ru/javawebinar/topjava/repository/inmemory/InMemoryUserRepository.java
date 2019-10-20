package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {

    @Autowired
    InMemoryMealRepository memoryMealRepository;

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private ConcurrentMap<Integer, User> userByIdStorage = new ConcurrentHashMap<>();
    private ConcurrentMap<String, User> userByEmailStorage = new ConcurrentHashMap<>();
    private AtomicInteger nextIdValue = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if (userByIdStorage.get(id) == null) return false;
        User user = userByIdStorage.get(id);
        memoryMealRepository.getAll(user.getId()).forEach(el -> memoryMealRepository.delete(el.getId(),el.getUserId()));
        return (userByIdStorage.remove(id) != null && userByEmailStorage.remove(user.getEmail()) != null);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()){
            user.setId(nextIdValue.getAndIncrement());
            userByIdStorage.put(user.getId(), user);
            userByEmailStorage.put(user.getEmail(), user);
        }
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userByIdStorage.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userByIdStorage.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userByEmailStorage.get(email);
    }
}