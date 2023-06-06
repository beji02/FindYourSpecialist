package fys.fysserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class FysServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(FysServerApplication.class);

    public static void main(String[] args) {
        logger.info("Server starting...");
        SpringApplication.run(FysServerApplication.class, args);
    }
}
