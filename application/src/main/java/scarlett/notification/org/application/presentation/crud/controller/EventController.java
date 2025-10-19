package scarlett.notification.org.application.presentation.crud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scarlett.notification.org.application.presentation.crud.model.EventModel;
import scarlett.notification.org.application.presentation.crud.service.EventService;

@RequiredArgsConstructor
@RequestMapping(value = "events")
@RestController
public class EventController implements BaseController<EventModel, EventService> {
    private final EventService eventService;

    @Override
    public EventService getService() {
        return this.eventService;
    }
}
