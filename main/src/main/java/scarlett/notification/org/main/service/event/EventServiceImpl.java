package scarlett.notification.org.main.service.event;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.main.mapper.EventMapper;
import scarlett.notification.org.main.model.EventModel;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final BeanUtils beanUtils;

    @Override
    public EventModel create(EventModel eventModel) {
        EventEntity map = eventMapper.map(eventModel);
        EventEntity save = eventRepository.save(map);
        return eventMapper.map(save);
    }

    @Override
    public EventModel update(EventModel eventModel) {
        var dbVal = eventRepository.findById(eventModel.getId())
                                                .orElseThrow(EntityNotFoundException::new);
        try {
            beanUtils.copyProperties(dbVal, eventModel);
        } catch (Exception e) {
            throw new RuntimeException("update failed", e);
        }
        return eventMapper.map(dbVal);
    }

    @Override
    public List<EventModel> getAll() {
        return eventMapper.reverse(eventRepository.findAll());
    }

    @Override
    public EventModel getById(UUID id) {
        return eventRepository.findById(id).map(eventMapper::map).orElseThrow(EntityNotFoundException::new);
    }
}
