package scarlett.notification.org.persistence.entity.basic;

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
