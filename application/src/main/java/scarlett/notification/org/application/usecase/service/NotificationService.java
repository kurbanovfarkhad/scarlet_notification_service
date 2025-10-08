package scarlett.notification.org.application.usecase.service;

import scarlett.notification.org.application.usecase.sender.IntegrationResult;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.persistence.entity.DeliveryAttemptEntity;

public interface NotificationService {
    DeliveryAttemptEntity recordingAttempt(
            MessageInformation messageInformation,
            IntegrationResult send);
}
