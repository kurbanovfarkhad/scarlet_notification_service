package scarlett.notification.org.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

@Table
@Entity
public class EventEntity extends BaseEntity {
    private String name;
    private String description;
    private String schema;
}
