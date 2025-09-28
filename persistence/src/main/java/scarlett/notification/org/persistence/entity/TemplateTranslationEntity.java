package scarlett.notification.org.persistence.entity;

import scarlett.notification.org.persistence.entity.basic.BaseEntity;
import scarlett.notification.org.persistence.entity.enums.LocaleEmbeddable;

public class TemplateTranslationEntity extends BaseEntity {
    private String body;
    private String subject;
    private String name;
    private LocaleEmbeddable local;
}
