package scarlett.notification.org.application;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.integration.serialization.avro.AvroBaseSchemaMapper;
import scarlett.notification.org.integration.serialization.avro.AvroSchemaSerializerImpl;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@Testcontainers
@SpringBootTest
public class KafkaSmokeTest extends BaseIntegrationTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("apache/kafka-native:3.8.0")
    );


    @Test
    void produceConsume() throws Exception {
        String bootstrap = kafka.getBootstrapServers();

        // Конфигурируем Kafka-продюсер
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                          "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                          "org.apache.kafka.common.serialization.ByteArraySerializer");


        AvroSchemaSerializerImpl serializer = new AvroSchemaSerializerImpl(
                Mappers.getMapper(AvroBaseSchemaMapper.class));

        // Создаём продюсер и отправляем одно сообщение в demo-topic
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(producerProps);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>("quickstart-events", "my-key",
                                                                     serializer.serialize(cretePayload()));
        record.headers().add(new RecordHeader("message-type", "AVRO".getBytes()));
    }

    private QueuePayload cretePayload() {
        QueuePayload payload = new QueuePayload();
        payload.setProperties(Map.of("message", "Hello World", "subject", "Title"));
        payload.setApplicationId("applicationId");
        payload.setEmail("email");
        payload.setEventName("eventName");
        payload.setPhoneNumber("phoneNumber");
        payload.setUserId(UUID.randomUUID());
        payload.setLocale(LocaleEmbeddable.ru);
        return payload;
    }
}
