package scarlett.notification.org.application.presentation.crud.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.application.presentation.crud.model.TemplateTranslationModel;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;

@Mapper(componentModel = "spring")
public interface TemplateTranslationMapper extends BaseMapper<TemplateTranslationEntity, TemplateTranslationModel> {
}
