package service;

import dao.UserDao;
import dao.UserDaoHibernateImpl;
import dao.UserDaoJDBCImpl;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    //UserDao userDao = new UserDaoJDBCImpl();
    private final UserDao userDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("Add user:  " + name + " " + lastName + " " + age);

    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        users.stream().forEach(user -> System.out.println(user.toString()));
        return users;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }


}