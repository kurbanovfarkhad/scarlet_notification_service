package scarlett.notification.org.application.presentation.crud.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.application.presentation.crud.model.UserPreferenceModel;
import scarlett.notification.org.persistence.entity.UserPreferenceEntity;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper extends BaseMapper<UserPreferenceEntity, UserPreferenceModel> {
}
