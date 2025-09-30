package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.infra.model.TemplateTranslationModel;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;

@Mapper(componentModel = "spring")
public interface TemplateTranslationMapper extends BaseMapper<TemplateTranslationEntity, TemplateTranslationModel> {
}
