package scarlett.notification.org.main.service.template;

import scarlett.notification.org.main.model.TemplateModel;

import java.util.List;
import java.util.UUID;

public interface TemplateService {
    List<TemplateModel> getAll();

    TemplateModel getById(UUID templateId);

    TemplateModel create(TemplateModel templateModel);

    TemplateModel update(TemplateModel templateModel);
}
