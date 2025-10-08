package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.infra.model.EventModel;
import scarlett.notification.org.persistence.entity.EventEntity;

@Mapper(componentModel = "spring", uses = {TemplateMapper.class})
public interface EventMapper extends BaseMapper<EventEntity, EventModel> {
}
