package fys.fyspersistence.announcements;

import fys.fysmodel.Announcement;
import fys.fysmodel.Field;
import fys.fysmodel.Reservation;
import fys.fyspersistence.Repository;

public interface AnnouncementsRepository extends Repository<Integer, Announcement> {
    Iterable<Field> findAllFields();

    Field findFieldById(Integer id);

    Reservation insertReservation(Reservation reservation);
}
