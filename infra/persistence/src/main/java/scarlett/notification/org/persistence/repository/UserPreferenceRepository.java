package scarlett.notification.org.persistence.repository;

import org.springframework.stereotype.Repository;
import scarlett.notification.org.persistence.entity.UserPreferenceEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends BaseCrudRepository<UserPreferenceEntity> {

    Optional<UserPreferenceEntity> findByUserId(UUID userId);
}
