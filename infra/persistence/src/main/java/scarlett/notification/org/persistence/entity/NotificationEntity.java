package scarlett.notification.org.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.DeliveryStatus;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeliveryStatus status;

    @Embedded
    private Recipient recipient;

    @Column(name = "event_id", columnDefinition = "uuid", nullable = false, unique = true)
    private UUID eventId;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "subject")
    private String subject;

    @OneToMany
    @JoinColumn(name = "notification_id", nullable = false)
    @OrderBy("createdDate DESC")
    private List<DeliveryAttemptEntity> deliveryAttempts = new ArrayList<>();

    @Transient
    public void addDeliveryAttempt(DeliveryAttemptEntity deliveryAttempt) {
        deliveryAttempts.add(deliveryAttempt);
    }

    @Transient
    public DeliveryAttemptEntity getLastDelivery() {
        return deliveryAttempts.isEmpty() ? null : deliveryAttempts.get(0);
    }
}

