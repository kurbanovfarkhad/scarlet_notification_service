package scarlett.notification.org.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_preference")
public class UserPreferenceEntity extends BaseEntity {
    @Column(name = "default_channel_type", nullable = false)
    private ChannelType defaultChannelType;
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;
}
