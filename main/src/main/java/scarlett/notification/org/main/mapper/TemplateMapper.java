package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.main.model.EventModel;
import scarlett.notification.org.main.model.TemplateModel;
import scarlett.notification.org.persistence.entity.EventEntity;
import scarlett.notification.org.persistence.entity.TemplateEntity;

@Mapper(componentModel = "spring", uses = {TemplateTranslationMapper.class})
public interface TemplateMapper extends BaseMapper<TemplateEntity, TemplateModel>{}
