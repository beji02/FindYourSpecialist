package fys.fyspersistence.announcements;

import fys.fysmodel.Announcement;
import fys.fysmodel.Field;
import fys.fysmodel.Reservation;
import fys.fyspersistence.exceptions.NonexistentEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Iterable<Field> findAllFields() {
        logger.traceEntry("Finding all fields");
        Session session = sessionFactory.getCurrentSession();
        logger.traceExit("All fields found");
        return session.createQuery("from Field", Field.class).list();
    }

    @Override
    public Field findFieldById(Integer id) {
        logger.traceEntry("Finding field by id " + id);
        Session session = sessionFactory.getCurrentSession();
        Field field = session.get(Field.class, id);
        logger.traceExit("Field found" + field);
        return field;
    }

    @Override
    public Reservation insertReservation(Reservation reservation) {
        logger.traceEntry("Inserting reservation " + reservation);
        Session session = sessionFactory.getCurrentSession();
        session.persist(reservation);
        logger.traceExit("Reservation inserted");
        return reservation;
    }

    @Override
    public Reservation findReservationById(Integer scheduleId) {
        logger.traceEntry("Finding reservation by id " + scheduleId);
        Session session = sessionFactory.getCurrentSession();
        Reservation reservation = session.get(Reservation.class, scheduleId);
        logger.traceExit("Reservation found" + reservation);
        return reservation;
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        logger.traceEntry("Deleting reservation " + reservation);
        Session session = sessionFactory.getCurrentSession();
        session.remove(reservation);
        logger.traceExit("Reservation deleted");
    }

    @Override
    public void modifyReservation(Reservation reservation) {
        logger.traceEntry("Modifying reservation " + reservation);
        Session session = sessionFactory.getCurrentSession();
        session.merge(reservation);
        logger.traceExit("Reservation modified");
    }
}
