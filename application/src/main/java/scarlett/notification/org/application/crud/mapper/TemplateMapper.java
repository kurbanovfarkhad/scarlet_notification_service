package scarlett.notification.org.application.crud.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.application.crud.model.TemplateModel;
import scarlett.notification.org.persistence.entity.TemplateEntity;

@Mapper(componentModel = "spring", uses = {TemplateTranslationMapper.class})
public interface TemplateMapper extends BaseMapper<TemplateEntity, TemplateModel> {

}
