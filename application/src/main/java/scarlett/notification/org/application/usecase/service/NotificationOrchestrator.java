package scarlett.notification.org.application.usecase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import scarlett.notification.org.application.usecase.sender.SendersChain;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationOrchestrator {
    private final ProcessingService processingService;
    private final NotificationService notificationService;

    public void handle(
            QueuePayload payload,
            String source) {
        var idempotencyKey = payload.getEventId();
        log.info("[HANDLE] Started processing message | source={} | idempotencyKey={} | payloadType={}",
                 source, idempotencyKey, payload != null ? payload.getClass().getSimpleName() : "null");
        notificationService.createNotification(payload, source, idempotencyKey);
        log.debug("[HANDLE] Notification created successfully | source={} | idempotencyKey={}", source,
                  idempotencyKey);
        try {
            List<SendersChain> process = processingService.process(payload);
            for (SendersChain sendersChain : process) {
                sendersChain.doChain();
            }
        } catch (Exception e) {
            log.error("[HANDLE] Failed to handle message | source={} | idempotencyKey={} | error={}",
                      source, idempotencyKey, e.getMessage(), e);
        }
        // change status
        notificationService.finalizeNotification(idempotencyKey);

        log.trace("[HANDLE] Main thread finished execution | idempotencyKey={}", idempotencyKey);
    }


}
