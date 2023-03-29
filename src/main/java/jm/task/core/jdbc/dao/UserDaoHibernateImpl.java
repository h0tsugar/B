package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.*;
import java.util.List;


public class UserDaoHibernateImpl extends Util implements UserDao {
    Connection connection = getConnection();
    UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS (" +
                "ID BEGINT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "USERNAME VARCHAR(255) NOT NULL," +
                "LASTNAME VARCHAR(30)," +
                "AGE INT NOT NULL)";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("Таблица создана");
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS";
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("Таблица удалена");
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name,lastName,age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при добавлении пользователя");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении пользователя по id");
        }
//        try (Session session = Util.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.delete(session.get(User.class, id));
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            userList = session.createQuery("FROM User", User.class).getResultList();
            session.getTransaction().commit();
            System.out.println("Сохраненно в лист");
            return userList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
//        try (Session session = Util.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            session.createQuery("DELETE User");
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            throw new RuntimeException("Ошибка при очистке таблицы");
//        }
//    }
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM USERS").addEntity(User.class);
            session.getTransaction().commit();
            System.out.println("Таблица очищенна");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
