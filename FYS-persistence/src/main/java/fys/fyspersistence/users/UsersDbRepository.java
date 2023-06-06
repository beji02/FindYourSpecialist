package fys.fyspersistence.users;

import fys.fysmodel.Notification;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.exceptions.NonexistentEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.Set;

import java.util.Collections;
import java.util.List;

@Transactional
public class UsersDbRepository implements UsersRepository {
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findById(Integer id) {
        logger.traceEntry("Finding user by id " + id);
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);

        if(user == null) {
            logger.traceExit("User with id " + id + " does not exist");
        }
        logger.traceExit("User found");
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry("Finding all users");
        Session session = sessionFactory.getCurrentSession();
        logger.traceExit("All users found");
        return session.createQuery("from User", User.class).list();
    }

    @Override
    public Iterable<User> findAllUsers() {
        return this.findAll();
    }

    @Override
    public Iterable<Specialist> findAllSpecialists() {
        logger.traceEntry("Finding all specialists");
        Session session = sessionFactory.getCurrentSession();
        logger.traceExit("All specialists found");
        return session.createQuery("from Specialist", Specialist.class).list();
    }

    @Override
    public Specialist findSpecialistByUsername(String username) {
        logger.traceEntry("Finding specialist by username " + username);
        Session session = sessionFactory.getCurrentSession();
        List<Specialist> specialists = session.createQuery("from Specialist where username = :username", Specialist.class)
                .setParameter("username", username)
                .list();
        if (specialists.size() == 0) {
            logger.traceExit("Specialist with username " + username + " not found");
            return null;
        } else {
            logger.traceExit("Specialist found");
            return specialists.get(0);
        }
    }

    @Override
    public User add(User user) {
        logger.traceEntry("Adding user " + user);
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        logger.traceExit("User added");
        return user;
    }

    @Override
    public void remove(Integer id) {
        logger.traceEntry("Removing user with id " + id);
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.remove(user);
            logger.traceExit("User removed");
        } else {
            logger.traceExit("User with id " + id + " not found");
            throw new NonexistentEntityException("User with id " + id + " does not exist");
        }
    }

    @Override
    public void remove(String username) {
        logger.traceEntry("Removing user with username " + username);
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, username);
        if (user != null) {
            session.remove(user);
            logger.traceExit("User removed");
        } else {
            logger.traceExit("User with username " + username + " not found");
            throw new NonexistentEntityException("User with username " + username + " does not exist");
        }
    }

    @Override
    @Transactional
    public void remove(User user) {
        logger.traceEntry("Removing user " + user);
        Session session = sessionFactory.getCurrentSession();
        session.remove(user);
        logger.traceExit("User removed");
    }

    @Override
    public void modify(User user) {
        logger.traceEntry("Modifying user " + user);
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
        logger.traceExit("User modified");
    }

    @Override
    public User findByUsername(String username) {
        logger.traceEntry("Finding user by username " + username);
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session
                .createQuery("from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        if(users.isEmpty()) {
            logger.traceExit("User not found");
            //throw new NonexistentEntityException("User with username = " + username + " does not exist");
            return null;
        }
        logger.traceExit("User found");
        return users.get(0);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        logger.traceEntry("Finding user by username " + username + " and password " + password);
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session
                .createQuery("from User u where u.username = :username and u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();

        if(users.isEmpty()) {
            logger.traceExit("User not found");
            throw new NonexistentEntityException("User with username = " + username + " and password = " + password + " does not exist");
        }
        logger.traceExit("User found");
        return users.get(0);
    }

    @Override
    public void upgradeToSpecialist(User user) {
        logger.traceEntry("Saving Race {}", user);
        Specialist specialist = Specialist.build(user);
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                session.remove(user);
                transaction.commit();
                logger.traceExit();
            } catch (RuntimeException ex) {
                if(transaction != null) {
                    transaction.rollback();
                    logger.error("Error in adding race {}", user);
                }
            }
        }
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                session.persist(specialist);
                transaction.commit();
                logger.traceExit();
            } catch (RuntimeException ex) {
                if(transaction != null) {
                    transaction.rollback();
                    logger.error("Error in adding race {}", user);
                }
            }
        }
    }

    @Override
    public Iterable<Notification> findNotificationsByUsername(String username) {
        logger.traceEntry("Finding notifications by username: " + username);
        Session session = sessionFactory.getCurrentSession();
        List<Notification> notifications = session.createQuery(
                        "SELECT n FROM User u JOIN u.notifications n WHERE u.username = :username ORDER BY n.date DESC",
                        Notification.class
                )
                .setParameter("username", username)
                .getResultList();

        logger.traceExit("Notifications found");
        return notifications;
    }


    @Override
    public Notification findNotificationById(Integer notificationId) {
        logger.traceEntry("Finding notification by ID: " + notificationId);
        Session session = sessionFactory.getCurrentSession();

        Notification notification = session.createQuery("from Notification n where n.id = :notificationId", Notification.class)
                .setParameter("notificationId", notificationId)
                .uniqueResult();

        if (notification == null) {
            logger.traceExit("Notification not found");
        } else {
            logger.traceExit("Notification found");
        }

        return notification;
    }

}
