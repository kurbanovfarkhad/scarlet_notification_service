package scarlett.notification.org.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "template_translation")
public class TemplateTranslationEntity extends BaseEntity {
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "locale", nullable = false)
    private LocaleEmbeddable locale;
}
