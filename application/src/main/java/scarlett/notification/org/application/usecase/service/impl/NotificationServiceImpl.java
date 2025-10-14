package scarlett.notification.org.application.usecase.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.application.usecase.service.NotificationService;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.DeliveryStatus;
import scarlett.notification.org.persistence.entity.DeliveryAttemptEntity;
import scarlett.notification.org.persistence.entity.NotificationEntity;
import scarlett.notification.org.persistence.entity.Recipient;
import scarlett.notification.org.persistence.repository.NotificationRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final EntityManager entityManager;

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
        deliveryAttempt.setChannelType(messageInformation.getChannelType());
        deliveryAttempt.setProviderResponse(result.getResponse());
        notification.setSubject(messageInformation.getSubject());
        notification.setBody(messageInformation.getBody());
        notification.addDeliveryAttempt(deliveryAttempt);
        notification.setStatus(result.isSuccess() ? DeliveryStatus.DELIVERED : DeliveryStatus.FAILED);
        entityManager.persist(deliveryAttempt);
        notificationRepository.save(notification);
        return deliveryAttempt;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void createNotification(
            QueuePayload payload,
            String source,
            UUID idempotencyKey) {
        // create notification
        Recipient recipient = new Recipient();
        recipient.setEmail(payload.getEmail());
        recipient.setPhoneNumber(payload.getPhoneNumber());
        recipient.setUserId(payload.getUserId());
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setEventId(idempotencyKey);
        notificationEntity.setSource(source);
        notificationEntity.setStatus(DeliveryStatus.IN_PROGRESS);
        notificationEntity.setRecipient(recipient);
        notificationRepository.save(notificationEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void finalizeNotification(UUID idempotencyKey) {
        Optional<NotificationEntity> mayNotification = notificationRepository.findByEventId(idempotencyKey);
        if (mayNotification.isPresent()) {
            NotificationEntity notification = mayNotification.get();
            DeliveryAttemptEntity lastDelivery = notification.getLastDelivery();
            if (Objects.nonNull(lastDelivery)) {
                notification.setStatus(
                        Objects.nonNull(lastDelivery.getError()) ? DeliveryStatus.FAILED : DeliveryStatus.DELIVERED);
            } else {
                notification.setStatus(DeliveryStatus.FAILED);
            }
        } else {
            throw new IllegalStateException("Notification entity not found");
        }
    }
}
