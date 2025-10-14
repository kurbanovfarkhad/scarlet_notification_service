package scarlett.notification.org.application.presentation.crud.service;


import scarlett.notification.org.application.presentation.crud.model.UserPreferenceModel;

import java.util.UUID;

public interface UserPreferenceService extends BaseService<UserPreferenceModel> {
    UserPreferenceModel getUserPreferences(UUID userId);
}
