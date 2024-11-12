package dao;

import model.User;
import org.hibernate.Session;
import java.util.List;
import util.Util;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL, age INT NOT NULL)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String SELECT_ALL_USERS_SQL = " FROM User";
    private static final String TRUNCATE_TABLE_SQL = "TRUNCATE TABLE users";


    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_USERS_SQL, User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(TRUNCATE_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    }
}