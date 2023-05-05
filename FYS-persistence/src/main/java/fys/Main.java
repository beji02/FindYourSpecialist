package fys;

import fys.fysmodel.User;
import fys.fyspersistence.users.UsersRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate.cfg.xml");

        UsersRepository usersRepository = context.getBean(UsersRepository.class);

        User user = new User();
        user.setFirstName("test first name");
        user.setLastName("test last name");
        user.setUsername("test username");
        user.setPassword("test password");

        usersRepository.add(user);
    }
}