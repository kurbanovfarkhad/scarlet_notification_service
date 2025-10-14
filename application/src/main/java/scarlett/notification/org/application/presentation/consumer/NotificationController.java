package scarlett.notification.org.application.presentation.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scarlett.notification.org.application.usecase.service.NotificationOrchestrator;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping(value = "notifications")
@RestController
public class NotificationController {
    private final NotificationOrchestrator orchestration;

    @PostMapping("send")
    public void sendNotification(
            @RequestBody
            QueuePayload queuePayload,
            @Header(value = "source") String source,
            @Header(value = "idempotency_key") UUID idempotencyKey) {
        queuePayload.setEventId(idempotencyKey);
        orchestration.handle(queuePayload, source, idempotencyKey);
    }
}
