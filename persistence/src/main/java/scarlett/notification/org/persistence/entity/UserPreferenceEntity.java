package scarlett.notification.org.persistence.entity;

import scarlett.notification.org.persistence.entity.basic.BaseEntity;

public class UserPreferenceEntity extends BaseEntity {
    private ChannelType defaultChannelType;
    private UUID userId;

}
