package fys.fyspersistence.specialists;

import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.exceptions.NonexistentEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SpecialistsDbRepository implements SpecialistsRepository{
    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Specialist findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Specialist> findAll() {
        return null;
    }

    @Override
    public Specialist add(Specialist entity) {
        return null;
    }

    @Override
    public void remove(Integer integer) {

    }

    @Override
    public void remove(Specialist entity) {

    }

    @Override
    public void modify(Specialist entity) {

    }

    @Override
    public Specialist findSpecialistByUsername(String username) {
        logger.traceEntry("Finding specialist by username " + username);
        Session session = sessionFactory.getCurrentSession();
        List<Specialist> specialists = session
                .createQuery("from Specialist s where s.username = :username", Specialist.class)
                .setParameter("username", username)
                .getResultList();

        if(specialists.isEmpty()) {
            logger.traceExit("Specialist not found");
            throw new NonexistentEntityException("Specialist with username = " + username + " does not exist");
        }
        logger.traceExit("Specialist found");
        return specialists.get(0);
    }
}
