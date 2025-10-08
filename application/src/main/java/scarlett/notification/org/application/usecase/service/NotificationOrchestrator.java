package scarlett.notification.org.application.usecase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class NotificationOrchestrator {
    private final ProcessingService processingService;
    private final ThreadPoolExecutor priorityExecutor;
    private final NotificationService notificationService;

    public void handle(
            QueuePayload payload,
            String source,
            UUID idempotencyKey) {
        notificationService.createNotificaiton(payload, source, idempotencyKey);
        // execute
        priorityExecutor.execute(() -> processingService.process(payload));
    }

}
