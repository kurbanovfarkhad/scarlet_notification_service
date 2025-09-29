package scarlett.notification.org.main.service;

import scarlett.notification.org.main.model.EventModel;

import java.util.List;
import java.util.UUID;

public interface EventService {
    EventModel create(EventModel eventModel);
    EventModel update(EventModel eventModel);
    List<EventModel> getAll();
    EventModel getById(UUID id);
}
