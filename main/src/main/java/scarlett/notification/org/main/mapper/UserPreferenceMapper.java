package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.infra.model.TemplateTranslationModel;
import scarlett.notification.org.infra.model.UserPreferenceModel;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;
import scarlett.notification.org.persistence.entity.UserPreferenceEntity;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper extends BaseMapper<UserPreferenceEntity, UserPreferenceModel> {
}
