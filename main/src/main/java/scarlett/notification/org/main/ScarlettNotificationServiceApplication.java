package scarlett.notification.org.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"scarlett.notification.org.main", "scarlett.notification.org.persistence", "scarlett.notification.org.common"})
@EntityScan(basePackages = "scarlett.notification.org.persistence.entity")
@EnableJpaRepositories(basePackages = "scarlett.notification.org.persistence.repository")
public class ScarlettNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScarlettNotificationServiceApplication.class, args);
    }

}
