package scarlett.notification.org.application.presentation.crud.mapper.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class EventMapperHelper {
    private final EventRepository eventRepository;

    @Transactional
    public List<EventEntity> mapEventIdsToEntities(List<UUID> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            return new ArrayList<>();
        }
        return eventRepository.findAllById(eventIds);
    }

    @Transactional
    public List<UUID> mapEventsToIds(List<EventEntity> events) {
        if (events == null || events.isEmpty()) {
            return new ArrayList<>();
        }
        return events.stream().map(EventEntity::getId).collect(Collectors.toList());
    }
}
