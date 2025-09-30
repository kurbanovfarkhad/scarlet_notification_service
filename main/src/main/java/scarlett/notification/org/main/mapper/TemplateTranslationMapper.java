package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.main.model.TemplateTranslationModel;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;

@Mapper
public interface TemplateTranslationMapper extends BaseMapper<TemplateTranslationEntity, TemplateTranslationModel> {
}
