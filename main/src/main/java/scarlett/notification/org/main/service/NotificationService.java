package scarlett.notification.org.main.service;

import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.main.sender.IntegrationResult;
import scarlett.notification.org.persistence.entity.DeliveryAttemptEntity;

public interface NotificationService {
    DeliveryAttemptEntity recordingAttempt(
            MessageInformation messageInformation,
            IntegrationResult send);
}
