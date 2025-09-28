package scarlett.notification.org.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "delivery_attempt")
public class DeliveryAttemptEntity extends BaseEntity {
    @ManyToOne
    private NotificationEntity notification;
    @Column(name = "attempt", nullable = false)
    private int attempt;
    @Column(name = "provider_response", nullable = false)
    private String providerResponse;
    @Column(name = "error", nullable = false)
    private String error;
    @Column(name = "payload", nullable = false)
    private String payload;
}
