package scarlett.notification.org.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.Priority;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "notification_template")
public class TemplateEntity extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "template_id")
    private List<TemplateTranslationEntity> translations = new ArrayList<>();

    @ElementCollection(targetClass = ChannelType.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "template_allowed_channels",
            joinColumns = @JoinColumn(name = "template_id")
    )
    @Column(name = "allowed_channels", nullable = false)
    private List<ChannelType> allowedChannel;

    @Column(name = "channel", nullable = false)
    private ChannelType defaultChannel;

    @Column(name = "delivery_attempts", nullable = false)
    private int deliveryAttempts;

    @Column(name = "priority", nullable = false)
    private Priority priority;

    @ManyToMany
    @JoinTable(
            name = "template_event",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<EventEntity> events = new ArrayList<>();
}
