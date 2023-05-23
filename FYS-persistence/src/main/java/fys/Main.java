package fys;
import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fysmodel.User;
import fys.fyspersistence.announcements.AnnouncementsDbRepository;
import fys.fyspersistence.announcements.AnnouncementsRepository;
import fys.fyspersistence.users.UsersRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate.cfg.xml");

        UsersRepository usersRepository = context.getBean(UsersRepository.class);

//        User user = new User();
//        user.setFirstName("test first name");
//        user.setLastName("test last name");
//        user.setUsername("admin");
//        user.setPassword("test password");

//        //usersRepository.add(user);
//
//        Specialist specialist = (Specialist)usersRepository.findByUsername("deiubejan");
//        System.out.println("***********************************************");
//        System.out.println(specialist.getDescription());
//        System.out.println("***********************************************");
//
//
//        LocalDate date = LocalDate.now();
//        LocalDate date2 = LocalDate.now().plusDays(1);
//        Set<LocalDate> dates = new HashSet<>();
//        dates.add(date);
//        dates.add(date2);
//
//
//
//        // add an announcement
//
//        // add workdays to the announcement
//        //announcement.setWorkDays(dates);
//
//        AnnouncementsRepository announcementsRepository = context.getBean(AnnouncementsRepository.class);
//        Announcement announcement = announcementsRepository.findById(13);
//
//        //announcement.setWorkDays(dates);
//        //announcementsRepository.modify(announcement);
//
//        announcement.getReservations().forEach(System.out::println);
    }
}