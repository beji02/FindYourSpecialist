package fys.fyspersistence.users;

import fys.fysmodel.Notification;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.Repository;

public interface UsersRepository extends Repository<Integer, User> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    Iterable<User> findAllUsers();

    Iterable<Specialist> findAllSpecialists();

    Specialist findSpecialistByUsername(String username);
    void remove(String username);
    void upgradeToSpecialist(User user);

    Iterable<Notification> findNotificationsByUsername(String username);

    Notification findNotificationById(Integer notificationId);
}
