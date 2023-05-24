package fys.fyspersistence.users;

import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.exceptions.NonexistentEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void add(User user) {
        logger.traceEntry("Adding user " + user);
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        logger.traceExit("User added");
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
            throw new NonexistentEntityException("User with username = " + username + " does not exist");
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
        Specialist specialist = Specialist.build(user);
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try{
                transaction = session.beginTransaction();
                session.save(specialist);
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
}
