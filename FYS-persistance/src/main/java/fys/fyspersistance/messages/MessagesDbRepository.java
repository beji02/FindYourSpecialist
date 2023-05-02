package fys.fyspersistance.messages;

import fys.fysmodel.Message;
import fys.fysmodel.User;
import fys.fyspersistance.exceptions.NonexistentEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.List;

public class MessagesDbRepository implements MessagesRepository {
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Message findById(Integer integer) {
        throw new NotYetImplementedException("MessagesDbRepository.findById() is not yet implemented");
    }

    @Override
    public Iterable<Message> findAll() {
        logger.traceEntry("Finding all messages");
        Session session = sessionFactory.getCurrentSession();
        logger.traceExit("All messages found");
        return session.createQuery("from Message", Message.class).list();
    }

    @Override
    public void add(Message message) {
        logger.traceEntry("Adding message " + message);
        Session session = sessionFactory.getCurrentSession();
        session.persist(message);
        logger.traceExit("Message added");
    }

    @Override
    public void remove(Integer id) {
        logger.traceEntry("Removing message with id " + id);
        Session session = sessionFactory.getCurrentSession();
        Message message = session.get(Message.class, id);
        if (message != null) {
            session.remove(message);
            logger.traceExit("Message removed");
        } else {
            logger.traceExit("Message with id " + id + " not found");
            throw new NonexistentEntityException("User with id " + id + " does not exist");
        }
    }

    @Override
    public void remove(Message message) {
        logger.traceEntry("Removing message " + message);
        Session session = sessionFactory.getCurrentSession();
        session.remove(message);
        logger.traceExit("Message removed");
    }

    @Override
    public void modify(Message message) {
        logger.traceEntry("Modifying message " + message);
        Session session = sessionFactory.getCurrentSession();
        session.merge(message);
        logger.traceExit("Message modified");
    }

    @Override
    public Iterable<Message> getUserMessages(User user) {
        logger.traceEntry("Finding all messages for user " + user);
        Session session = sessionFactory.getCurrentSession();
        List<Message> messages = session
                .createQuery("from Message m where m.user = :user", Message.class)
                .setParameter("user", user)
                .getResultList();
        logger.traceEntry("Found all messages for user " + user);
        return messages;
    }
}
