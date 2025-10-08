package scarlett.notification.org.infra.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateTranslationModel extends BaseModel {
    private String body;
    private String subject;
    private String name;
    private LocaleEmbeddable locale;
}
