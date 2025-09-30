package scarlett.notification.org.persistence.repository;

import org.springframework.stereotype.Repository;
import scarlett.notification.org.persistence.entity.EventEntity;

@Repository
public interface EventRepository extends BaseCrudRepository<EventEntity> {
}
