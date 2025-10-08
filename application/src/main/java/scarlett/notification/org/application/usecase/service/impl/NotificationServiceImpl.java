package scarlett.notification.org.application.usecase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.usecase.sender.IntegrationResult;
import scarlett.notification.org.application.usecase.service.NotificationService;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.persistence.entity.DeliveryAttemptEntity;
import scarlett.notification.org.persistence.entity.NotificationEntity;
import scarlett.notification.org.persistence.repository.NotificationRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public DeliveryAttemptEntity recordingAttempt(
            MessageInformation messageInformation,
            IntegrationResult result) {
        UUID eventId = messageInformation.getEventId();
        Optional<NotificationEntity> mayNotification = notificationRepository.findByEventId(eventId);
        NotificationEntity notification = null;
        if (mayNotification.isPresent()) {
            notification = mayNotification.get();
        } else {
            throw new IllegalStateException("Notification entity not found");
        }
        DeliveryAttemptEntity deliveryAttempt = new DeliveryAttemptEntity();
        deliveryAttempt.setAttempt(messageInformation.getRemainingAttempts());
        deliveryAttempt.setError(result.getError());
        deliveryAttempt.setProviderResponse(result.getResponse());
        notification.setSubject(messageInformation.getSubject());
        notification.setBody(messageInformation.getBody());
        notification.addDeliveryAttempt(deliveryAttempt);
        return deliveryAttempt;
    }
}
