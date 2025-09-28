package scarlett.notification.org.integration.entity.basic;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setSource("integration-service");
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
    }
}
