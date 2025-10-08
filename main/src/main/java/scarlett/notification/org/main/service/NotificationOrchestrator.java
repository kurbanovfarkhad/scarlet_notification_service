package scarlett.notification.org.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.DeliveryStatus;
import scarlett.notification.org.persistence.entity.NotificationEntity;
import scarlett.notification.org.persistence.entity.Recipient;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class NotificationOrchestrator {
    private final ProcessingService processingService;
    private final ThreadPoolExecutor priorityExecutor;

    public void handle(
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
        // execute
        priorityExecutor.execute(() -> processingService.process(payload));
    }
}
