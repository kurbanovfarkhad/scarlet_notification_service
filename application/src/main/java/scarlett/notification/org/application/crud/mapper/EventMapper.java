package scarlett.notification.org.application.crud.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.application.crud.model.EventModel;
import scarlett.notification.org.persistence.entity.EventEntity;

@Mapper(componentModel = "spring", uses = {TemplateMapper.class})
public interface EventMapper extends BaseMapper<EventEntity, EventModel> {
}
