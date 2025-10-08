package scarlett.notification.org.integration.serialization.avro;

import org.mapstruct.Mapper;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.avro.AuroQueuePayload;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AvroBaseSchemaMapper {

    QueuePayload map(AuroQueuePayload avro);

    AuroQueuePayload map(QueuePayload payload);

    default LocaleEmbeddable mapLocale(CharSequence language) {
        if (language == null) {
            return null;
        }
        return LocaleEmbeddable.valueOf(language.toString());
    }

    default String mapCharSequence(CharSequence cs) {
        return cs == null ? null : cs.toString();
    }

    // UUID -> CharSequence
    default CharSequence map(UUID value) {
        return value != null ? value.toString() : null;
    }

    // CharSequence -> UUID
    default UUID map(CharSequence value) {
        return value != null ? UUID.fromString(value.toString()) : null;
    }
}
