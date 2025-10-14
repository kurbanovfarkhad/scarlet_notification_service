package scarlett.notification.org.integration.avro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;
import scarlett.notification.org.integration.serialization.avro.AvroBaseSchemaMapperImpl;
import scarlett.notification.org.integration.serialization.avro.AvroSchemaSerializerImpl;

import java.util.Map;

class AvroSchemaDeserializerImplTest {
    private final AvroSchemaSerializerImpl avroSchemaSerializer = new AvroSchemaSerializerImpl(
            new AvroBaseSchemaMapperImpl());

    @Test
    void deserializePayload() {
        // given
        QueuePayload payload = new QueuePayload();
        payload.setProperties(Map.of("k1", "v1"));
        payload.setApplicationId("applicationId");
        payload.setEmail("email");
        payload.setEventName("eventName");
        payload.setPhoneNumber("phoneNumber");
        payload.setLocale(LocaleEmbeddable.ru);
        // when
        byte[] bytes = avroSchemaSerializer.serialize(payload);
        QueuePayload queuePayload = avroSchemaSerializer.deserialize(bytes);
        // then
        Assertions.assertEquals(queuePayload, payload);
    }

}