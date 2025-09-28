package scarlett.notification.org.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "scarlett.notification.org.persistence.entity")
public class ScarlettNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScarlettNotificationServiceApplication.class, args);
    }

}
