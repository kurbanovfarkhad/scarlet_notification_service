package scarlett.notification.org.persistence.entity;

import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.UUID;

public class NotificationEntity extends BaseEntity {
    private DeliveryStatus status;
    private ChannelType channelType;
    private UUID userId;
    private String recipient;
    private UUID eventId;
    private String body;
    private String subject;
    private TemplateEntity template;
}
