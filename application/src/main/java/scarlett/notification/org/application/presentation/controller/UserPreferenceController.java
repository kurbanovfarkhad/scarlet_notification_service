package scarlett.notification.org.application.presentation.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scarlett.notification.org.application.presentation.crud.model.UserPreferenceModel;
import scarlett.notification.org.application.presentation.crud.service.UserPreferenceService;

@RequiredArgsConstructor
@RequestMapping(value = "preferences")
@RestController
public class UserPreferenceController implements BaseController<UserPreferenceModel, UserPreferenceService> {
    private final UserPreferenceService userPreferenceService;

    @Override
    public UserPreferenceService getService() {
        return this.userPreferenceService;
    }
}
