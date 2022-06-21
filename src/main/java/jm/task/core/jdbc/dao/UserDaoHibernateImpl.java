package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }
    private static Session session;

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession() ) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS`clone`.`users` (" +
                    "`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`name` VARCHAR(45) NOT NULL, " +
                    "`lastName` VARCHAR(45) NOT NULL, " +
                    "`age` INT(3) NOT NULL, PRIMARY KEY (`id`), " +
                    "UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);").executeUpdate();
            System.out.println("Таблица пользователей создана");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            System.out.println("Таблица удалена");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            System.out.println("Пользователь " + name + " " + lastName + " добавлен");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete("id", user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createNativeQuery("SELECT * FROM users;").addEntity(User.class);
            userList = query.list();
            System.out.println(userList);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users;").executeUpdate();
            System.out.println("Таблица пользователей очищена");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
