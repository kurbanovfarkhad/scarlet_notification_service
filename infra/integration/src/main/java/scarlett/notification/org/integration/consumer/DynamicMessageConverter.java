package scarlett.notification.org.integration.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.SerializationType;
import scarlett.notification.org.integration.serialization.SchemaSerializer;
import scarlett.notification.org.integration.serialization.SerializerFactory;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DynamicMessageConverter implements RecordMessageConverter {

    private final SerializerFactory factory;

    @Override
    public Message<?> toMessage(
            ConsumerRecord<?, ?> record,
            Acknowledgment acknowledgment,
            Consumer<?, ?> consumer,
            Type payloadType) {
        try {
            Headers headers = record.headers();
            Header header = headers.lastHeader("message-type");

            if (header == null) {
                throw new IllegalStateException("Missing message-type header");
            }

            String typeStr = new String(header.value(), StandardCharsets.UTF_8);
            SchemaSerializer serializer = factory.getSerializer(SerializationType.valueOf(typeStr));
            QueuePayload obj = serializer.deserialize((byte[]) record.value());

            return MessageBuilder
                    .withPayload(obj)
                    .copyHeaders(toHeaderMap(headers))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Avro conversion failed", e);
        }
    }

    @Override
    public ProducerRecord<?, ?> fromMessage(
            Message<?> message,
            String defaultTopic) {
        throw new UnsupportedOperationException();
    }

    private Map<String, Object> toHeaderMap(Headers headers) {
        Map<String, Object> map = new HashMap<>();
        for (Header header : headers) {
            map.put(header.key(), new String(header.value(), StandardCharsets.UTF_8));
        }
        return map;
    }
}
