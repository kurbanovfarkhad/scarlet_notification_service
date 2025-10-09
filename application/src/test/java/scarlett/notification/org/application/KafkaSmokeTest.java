package scarlett.notification.org.application;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Testcontainers
@SpringBootTest
public class KafkaSmokeTest extends BaseIntegrationTest {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("apache/kafka-native:3.8.0")
    );

    @BeforeAll
    static void setup() {
        String bootstrap = kafka.getBootstrapServers();

        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                          "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                          "org.apache.kafka.common.serialization.ByteArraySerializer");
        try (AdminClient admin = AdminClient.create(producerProps)) {

            NewTopic topic = new NewTopic("quickstart-events", 1, (short) 1);
            admin.createTopics(List.of(topic)).all().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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
        record.headers().add(new RecordHeader("idempotency-key", "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d".getBytes()));
        record.headers().add(new RecordHeader("source", "console".getBytes()));
        producer.send(record);
        producer.flush();
        producer.close();
        Thread.sleep(1000000);
    }

    private QueuePayload cretePayload() {
        QueuePayload payload = new QueuePayload();
        payload.setProperties(Map.of("message", "pisyu", "title", "huy"));
        payload.setApplicationId("console");
        payload.setEmail("er@er.er");
        payload.setEventName("test");
        payload.setPhoneNumber("77022573825");
        payload.setUserId(UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"));
        payload.setLocale(LocaleEmbeddable.ru);
        return payload;
    }
}
