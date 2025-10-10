package scarlett.notification.org.application.usecase.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.application.usecase.service.NotificationService;
import scarlett.notification.org.common.model.MessageInformation;

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
