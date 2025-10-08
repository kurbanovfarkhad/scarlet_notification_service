package scarlett.notification.org.main;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.main.service.NotificationOrchestrator;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NotificationConsumer {

    private final NotificationOrchestrator orchestration;


    @KafkaListener(topics = "${notification.topic}")
    public void handler(
            QueuePayload payload,
            @Header(KafkaHeaders.RECEIVED_KEY) String source,
            @Header(KafkaHeaders.RECEIVED_KEY) UUID idempotencyKey) {
        orchestration.handle(payload, source, idempotencyKey);
    }
}
