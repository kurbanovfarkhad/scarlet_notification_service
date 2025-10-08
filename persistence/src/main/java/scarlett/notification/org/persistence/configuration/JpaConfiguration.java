package scarlett.notification.org.persistence.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfiguration {
    @Bean
    public ObjectMapper test() {
        System.out.println("test");
        return new ObjectMapper();
    }
}
