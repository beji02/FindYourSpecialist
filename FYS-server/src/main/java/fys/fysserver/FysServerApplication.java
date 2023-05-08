package fys.fysserver;

import fys.fyspersistence.users.UsersRepository;
import fys.fysserver.api.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class FysServerApplication {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("hibernate.cfg.xml");
        UserService userService = context.getBean(UserService.class);

        var user = userService.login("test username", "test password");


        SpringApplication.run(FysServerApplication.class, args);
    }

}
