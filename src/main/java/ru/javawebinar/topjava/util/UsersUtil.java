package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsersUtil {

    public static final Set<Role> ROLES_GOD = new HashSet<>(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER));
    public static final Set<Role> ROLES_USER = new HashSet<>(Arrays.asList(Role.ROLE_USER));

    public static final List<User> USERS = Arrays.asList(
            new User(null, "user1", "email-1@email.ru", "passs1", 2000, true, ROLES_GOD),
            new User(null, "user2", "email-2@email.ru", "passs2", 3000, true, ROLES_USER),
            new User(null, "user2", "email-1@email.ru", "passs1", 3000, true, ROLES_USER),
            new User(null, "user2", "email-3@email.ru", "passs3", 3000, true, ROLES_USER)
    );
}
