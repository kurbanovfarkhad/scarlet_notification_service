package scarlett.notification.org.infra.service;

import scarlett.notification.org.infra.model.UserPreferenceModel;

import java.util.UUID;

public interface UserPreferenceService extends BaseService<UserPreferenceModel>{
    UserPreferenceModel getUserPreferences(UUID userId);
}
