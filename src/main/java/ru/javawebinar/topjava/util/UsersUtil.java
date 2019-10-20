package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "Vasiliy", "vasiliy@gmail.com", "somePassword", Role.ROLE_USER),
            new User(2, "Dmitry", "dmitry@gmail.com", "dmitryPassword", Role.ROLE_USER)
    );
}