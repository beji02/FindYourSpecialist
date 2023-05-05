package fys.fysserver;

import fys.fyspersistence.users.UsersRepository;
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
        SpringApplication.run(FysServerApplication.class, args);
    }

}
