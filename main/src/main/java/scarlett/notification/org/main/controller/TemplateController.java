package scarlett.notification.org.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scarlett.notification.org.main.model.EventModel;
import scarlett.notification.org.main.model.TemplateModel;
import scarlett.notification.org.main.service.template.TemplateService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(value = "tempaltes")
@RestController
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping
    public List<TemplateModel> getEvents() {
        return templateService.getAll();
    }

    @GetMapping(value = "/{eventId}")
    public TemplateModel getEvent(@PathVariable UUID eventId) {
        return templateService.getById(eventId);
    }

    @PostMapping
    public TemplateModel create(@RequestBody TemplateModel eventModel) {
        return templateService.create(eventModel);
    }

    @PatchMapping
    public TemplateModel update(@RequestBody TemplateModel eventModel) {
        return templateService.update(eventModel);
    }
}
