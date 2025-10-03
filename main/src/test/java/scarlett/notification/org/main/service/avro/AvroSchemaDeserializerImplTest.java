package scarlett.notification.org.main.service.avro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;

import java.util.Map;

class AvroSchemaDeserializerImplTest {
    private final AvroSchemaDeserializerImpl avroSchemaDeserializer = new AvroSchemaDeserializerImpl(
            new AvroBaseSchemaMapperImpl());
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
        byte[] bytes = avroSchemaSerializer.serializePayload(payload);
        QueuePayload queuePayload = avroSchemaDeserializer.deserializePayload(bytes);
        // then
        Assertions.assertEquals(queuePayload, payload);
    }

}