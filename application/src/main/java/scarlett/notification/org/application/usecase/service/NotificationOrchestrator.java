package scarlett.notification.org.application.usecase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
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
        log.info("[HANDLE] Started processing message | source={} | idempotencyKey={} | payloadType={}",
                 source, idempotencyKey, payload != null ? payload.getClass().getSimpleName() : "null");
        try {
            notificationService.createNotificaiton(payload, source, idempotencyKey);
            log.debug("[HANDLE] Notification created successfully | source={} | idempotencyKey={}", source,
                      idempotencyKey);

            // execute asynchronously
            priorityExecutor.execute(() -> {
                log.debug("[HANDLE] Executing async processing | idempotencyKey={}", idempotencyKey);
                try {
                    processingService.process(payload);
                    log.info("[HANDLE] Async processing completed successfully | idempotencyKey={}", idempotencyKey);
                } catch (Exception e) {
                    log.error("[HANDLE] Error during async processing | idempotencyKey={} | error={}",
                              idempotencyKey, e.getMessage(), e);
                }
            });

        } catch (Exception e) {
            log.error("[HANDLE] Failed to handle message | source={} | idempotencyKey={} | error={}",
                      source, idempotencyKey, e.getMessage(), e);
        }

        log.trace("[HANDLE] Main thread finished execution | idempotencyKey={}", idempotencyKey);
    }


}
