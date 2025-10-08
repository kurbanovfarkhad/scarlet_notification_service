package scarlett.notification.org.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "scarlett.notification.org")
@EntityScan(basePackages = "scarlett.notification.org")
@EnableJpaRepositories(basePackages = "scarlett.notification.org")
@EnableJpaAuditing
public class ScarlettNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScarlettNotificationServiceApplication.class, args);
    }

}
