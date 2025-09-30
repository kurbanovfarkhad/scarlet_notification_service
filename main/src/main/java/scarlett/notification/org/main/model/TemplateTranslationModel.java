package scarlett.notification.org.main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.main.model.enums.LocaleEmbeddable;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateTranslationModel extends BaseModel {
    private String body;
    private String subject;
    private String name;
    private LocaleEmbeddable local;
}
