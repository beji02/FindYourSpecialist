package fys.fyspersistence.announcements;

import fys.fysmodel.Announcement;
import fys.fyspersistence.exceptions.NonexistentEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Transactional
public class AnnouncementsDbRepository implements AnnouncementsRepository {

    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Announcement findById(Integer id) {
        logger.traceEntry("Finding announcement by id " + id);
        Session session = sessionFactory.getCurrentSession();
        Announcement announcement = session.get(Announcement.class, id);

        if(announcement == null) {
            logger.traceExit("Announcement with id " + id + " does not exist");
        }
        logger.traceExit("Announcement found");
        return announcement;
    }

    @Override
    public Iterable<Announcement> findAll() {
        logger.traceEntry("Finding all announcements");
        Session session = sessionFactory.getCurrentSession();
        logger.traceExit("All announcements found");
        return session.createQuery("from Announcement", Announcement.class).list();
    }

    @Override
    public Announcement add(Announcement announcement) {
        logger.traceEntry("Adding announcement " + announcement);
        Session session = sessionFactory.getCurrentSession();
        session.persist(announcement);
        logger.traceExit("Announcement added");
        return announcement;
    }

    @Override
    public void remove(Integer id) {
        logger.traceEntry("Removing announcement with id " + id);
        Session session = sessionFactory.getCurrentSession();
        Announcement announcement = session.get(Announcement.class, id);
        if (announcement != null) {
            session.remove(announcement);
            logger.traceExit("Announcement removed");
        } else {
            logger.traceExit("Announcement with id " + id + " not found");
            throw new NonexistentEntityException("Announcement with id " + id + " does not exist");
        }
    }

    @Override
    public void remove(Announcement announcement) {
        logger.traceEntry("Removing announcement " + announcement);
        Session session = sessionFactory.getCurrentSession();
        session.remove(announcement);
        logger.traceExit("Announcement removed");
    }

    @Override
    public void modify(Announcement announcement) {
        logger.traceEntry("Modifying announcement " + announcement);
        Session session = sessionFactory.getCurrentSession();
        session.merge(announcement);
        logger.traceExit("Announcement modified");
    }
}
