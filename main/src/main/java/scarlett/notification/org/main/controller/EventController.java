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
import scarlett.notification.org.main.service.EventService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(value = "events")
@RestController
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<EventModel> getEvents() {
        return eventService.getAll();
    }

    @GetMapping(value = "/{eventId}")
    public EventModel getEvent(@PathVariable UUID eventId) {
        return eventService.getById(eventId);
    }

    @PostMapping
    public EventModel create(@RequestBody EventModel eventModel) {
        return eventService.create(eventModel);
    }

    @PatchMapping
    public EventModel update(@RequestBody EventModel eventModel) {
        return eventService.update(eventModel);
    }
}
