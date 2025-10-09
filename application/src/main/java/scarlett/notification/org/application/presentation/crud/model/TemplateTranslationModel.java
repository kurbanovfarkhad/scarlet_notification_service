package scarlett.notification.org.application.presentation.crud.model;

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
