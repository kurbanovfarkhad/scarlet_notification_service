package scarlett.notification.org.application.presentation.crud.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.presentation.crud.CrudService;
import scarlett.notification.org.application.presentation.crud.mapper.BaseMapper;
import scarlett.notification.org.application.presentation.crud.mapper.EventMapper;
import scarlett.notification.org.application.presentation.crud.model.EventModel;
import scarlett.notification.org.application.presentation.crud.service.EventService;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.repository.EventRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService, CrudService<EventModel, EventEntity, EventRepository> {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final BeanUtils beanUtils;

    @Override
    public EventRepository getRepository() {
        return this.eventRepository;
    }

    @Override
    public BaseMapper<EventEntity, EventModel> getMapper() {
        return this.eventMapper;
    }

    @Override
    public BeanUtils getBeanUtils() {
        return this.beanUtils;
    }
}
