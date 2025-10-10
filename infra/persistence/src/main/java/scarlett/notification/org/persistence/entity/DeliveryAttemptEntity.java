package scarlett.notification.org.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "delivery_attempt")
public class DeliveryAttemptEntity extends BaseEntity {
    @Column(name = "attempt", nullable = false)
    private int attempt;
    @Column(name = "provider_response")
    private String providerResponse;
    @Column(name = "error")
    private String error;
    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", nullable = false)
    private ChannelType channelType;
}
