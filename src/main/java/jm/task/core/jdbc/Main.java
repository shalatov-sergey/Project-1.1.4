package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Анна", "Крылова", (byte) 23);
        userService.saveUser("Ригина", "Ткачева", (byte) 56);
        userService.saveUser("Барик", "Барбариан", (byte) 21);
        userService.saveUser("Дмитрий", "Рогов", (byte) 57);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.getSessionFactory().close();
    }
}
