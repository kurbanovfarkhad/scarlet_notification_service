package scarlett.notification.org.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;
import scarlett.notification.org.persistence.entity.enums.ChannelType;
import scarlett.notification.org.persistence.entity.enums.OrderPriority;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "notification_template")
public class TemplateEntity extends BaseEntity {

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TemplateTranslationEntity> translations = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "template_event",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<EventEntity> events = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private OrderPriority priority;

    @ElementCollection(targetClass = ChannelType.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "template_allowed_channels",
            joinColumns = @JoinColumn(name = "template_id")
    )
    @Column(name = "channel", nullable = false)
    private List<ChannelType> allowedChannel;

    @Column(name = "delivery_attempts", nullable = false)
    private int deliveryAttempts;
}
