package scarlett.notification.org.application;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.mapstruct.factory.Mappers;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.integration.serialization.avro.AvroBaseSchemaMapper;
import scarlett.notification.org.integration.serialization.avro.AvroSchemaSerializerImpl;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class Producer {

    public static void main(String[] args) throws Exception {
        String bootstrap = "localhost:9092";
        String topic = "quickstart-events";

        // 1️⃣ Создание топика
        try (AdminClient admin = AdminClient.create(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap
                                                          ))) {
            NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
            admin.createTopics(List.of(newTopic)).all().get();
            System.out.println("Topic created: " + topic);
        } catch (Exception e) {
            System.out.println("Topic creation failed or already exists: " + e.getMessage());
        }

        // 2️⃣ Конфиг продюсера
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        // 3️⃣ Продюсер
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(producerProps);
        QueuePayload queuePayload = cretePayload();
        AvroSchemaSerializerImpl avroSchemaSerializer = new AvroSchemaSerializerImpl(
                Mappers.getMapper(AvroBaseSchemaMapper.class));
        // Пример payload
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, "my-key",
                                                                     avroSchemaSerializer.serialize(queuePayload));
        record.headers().add("message-type", "AVRO".getBytes(StandardCharsets.UTF_8));
        record.headers().add(new RecordHeader("idempotency-key", "9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d".getBytes()));
        record.headers().add(new RecordHeader("source", "console".getBytes()));
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("Sent to topic: " + metadata.topic() + ", partition: " + metadata.partition());
            }
        });
        producer.flush();
        producer.close();

        // 4️⃣ Конфиг консьюмера
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "notification");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(List.of(topic));

        // 5️⃣ Чтение сообщения
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(10));
        records.forEach(r -> {
            String payload = new String(r.value(), StandardCharsets.UTF_8);
            System.out.println("Received: " + payload + ", headers: " + r.headers());
        });

        consumer.close();
    }

    private static QueuePayload cretePayload() {
        QueuePayload payload = new QueuePayload();
        payload.setProperties(Map.of("message", "PIZDA", "title", "VOLOSATAYA"));
        payload.setApplicationId("console");
        payload.setEmail("er@er.er");
        payload.setEventName("TEST_EVENT");
        payload.setEventId(UUID.randomUUID());
        payload.setPhoneNumber("77022573825");
        payload.setUserId(UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d"));
        payload.setLocale(LocaleEmbeddable.ru);
        return payload;
    }
}
