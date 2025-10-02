package scarlett.notification.org.persistence.repository;

import org.springframework.stereotype.Repository;
import scarlett.notification.org.persistence.entity.EventEntity;

import java.util.Optional;

@Repository
public interface EventRepository extends BaseCrudRepository<EventEntity> {
    Optional<EventEntity> findByName(String eventName);
}
