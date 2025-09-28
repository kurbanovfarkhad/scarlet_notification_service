package scarlett.notification.org.persistence.entity;

import scarlett.notification.org.persistence.entity.basic.BaseEntity;

public class DeliveryAttemptEntity extends BaseEntity {
    private NotificationEntity notification;
    private int attempt;
    private String providerResponse;
    private String error;
    private String payload;
}
