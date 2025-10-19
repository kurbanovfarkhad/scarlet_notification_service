package scarlett.notification.org.application.presentation.crud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scarlett.notification.org.application.presentation.crud.model.TemplateModel;
import scarlett.notification.org.application.presentation.crud.service.TemplateService;

@RequiredArgsConstructor
@RequestMapping(value = "templates")
@RestController
public class TemplateController implements BaseController<TemplateModel, TemplateService> {
    private final TemplateService templateService;

    @Override
    public TemplateService getService() {
        return this.templateService;
    }
}
