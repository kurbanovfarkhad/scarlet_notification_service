package scarlett.notification.org.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.UUID;

public interface BaseCrudRepository<ENTITY extends BaseEntity> extends JpaRepository<ENTITY, UUID> {

}
