package scarlett.notification.org.application.presentation.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import scarlett.notification.org.application.usecase.service.NotificationOrchestrator;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationConsumer {

    private final NotificationOrchestrator orchestration;

    @KafkaListener(topics = "${notification.topic}")
    public void handler(
            QueuePayload payload,
            @Header("source") String source,
            @Header("idempotency-key") UUID idempotencyKey,
            Acknowledgment ack) {
        log.info("[CONSUMER] spotted event, payload {}, idempotencyKey {} and source {}", payload, idempotencyKey,
                 source);
        try {
            payload.setEventId(idempotencyKey);
            orchestration.handle(payload, source, idempotencyKey);
            log.info("[CONSUMER] Successfully processed message, payload {}, idempotencyKey {} and source {}", payload,
                     idempotencyKey, source);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("[CONSUMER] Failed to process message, payload: {}, idempotencyKey: {}, source: {}, error: {}",
                      payload, idempotencyKey, source, e.getMessage(), e);
        }
    }
}
