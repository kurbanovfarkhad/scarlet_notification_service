package scarlett.notification.org.persistence.entity;

import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.List;

public class TemplateEntity extends BaseEntity {
    private List<TemplateTranslationEntity> template;
    private EventEntity event;
    private Priority priority;
    private List<ChannelType> allowedChannel;
    private int deliveryAttempts;

}
