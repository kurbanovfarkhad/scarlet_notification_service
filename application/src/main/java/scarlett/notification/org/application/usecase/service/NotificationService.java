package scarlett.notification.org.application.usecase.service;

import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.persistence.entity.DeliveryAttemptEntity;

import java.util.UUID;

public interface NotificationService {
    DeliveryAttemptEntity recordingAttempt(
            MessageInformation messageInformation,
            IntegrationResult send);

    void createNotification(
            QueuePayload payload,
            String source,
            UUID idempotencyKey);

    void finalizeNotification(UUID idempotencyKey);
}
