package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util util = new Util();
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ruslan", "Zaitsev", (byte) 23);
        userService.saveUser("Dmitriy", "Gromov", (byte) 45);
        userService.saveUser("Oleg", "Ivanov", (byte) 28);
        userService.saveUser("Mikhail", "Popov", (byte) 20);
        userService.removeUserById(1);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
