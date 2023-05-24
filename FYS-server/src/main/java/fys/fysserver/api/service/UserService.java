package fys.fysserver.api.service;

import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.announcements.AnnouncementsRepository;
import fys.fyspersistence.users.UsersDbRepository;
import fys.fyspersistence.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UsersRepository usersRepository;

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
//    public void setAnnouncementsRepository(AnnouncementsRepository announcementsRepository) {
//        this.announcementsRepository = announcementsRepository;
//    }


    public UserService() {

    }


    public User register(String username, String password, String email) {
        System.out.println("register: " + username + " " + password + " " + email);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        usersRepository.add(user);
        user = usersRepository.findByUsernameAndPassword(username, password);

        return user;
    }

    public User findUserByUsername(String username) {
        System.out.println("findUserByUsername: " + username);

        return usersRepository.findByUsername(username);
    }

    @Transactional
    public void upgradeToSpecialist(User user) {
        System.out.println("upgradeToSpecialist: " + user.getUsername());

        Specialist specialist = Specialist.build(user);
        //usersRepository.remove(user);
        //usersRepository.add(specialist);

        usersRepository.upgradeToSpecialist(user);
    }

    public void updateUser(User user) {
        System.out.println("updateUser: " + user.getUsername() + " " + user.getPassword() + " " + user.getEmail());
        usersRepository.modify(user);
    }

    public void updateSpecialist(Specialist specialist) {
        System.out.println("updateUser: " + specialist.getUsername() + " " + specialist.getEmail());
        usersRepository.modify(specialist);
    }
}
