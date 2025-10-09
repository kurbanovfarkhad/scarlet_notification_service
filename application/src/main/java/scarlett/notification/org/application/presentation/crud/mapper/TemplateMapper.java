package scarlett.notification.org.application.presentation.crud.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.application.presentation.crud.mapper.helpers.EventMapperHelper;
import scarlett.notification.org.application.presentation.crud.model.TemplateModel;
import scarlett.notification.org.persistence.entity.TemplateEntity;

@Mapper(componentModel = "spring", uses = {TemplateTranslationMapper.class, EventMapperHelper.class})
public interface TemplateMapper extends BaseMapper<TemplateEntity, TemplateModel> {

}
