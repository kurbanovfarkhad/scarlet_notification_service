package scarlett.notification.org.main.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.main.service.NotificationService;

@RequiredArgsConstructor
@Component
public class DecoratedSenderAdapter {
    private final NotificationService notificationService;

    public IntegrationResult send(
            MessageInformation messageInformation,
            SenderAdapter senderAdapter) {
        try {
            IntegrationResult send = senderAdapter.send(messageInformation);
            notificationService.recordingAttempt(messageInformation, send);
            return send;

        } catch (Exception e) {
            throw new IllegalStateException("Exception occurred while sending notification", e);
        }
    }

}
