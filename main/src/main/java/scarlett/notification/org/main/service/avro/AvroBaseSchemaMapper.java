package scarlett.notification.org.main.service.avro;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import scarlett.notification.org.common.model.QueuePayload;
import scarlett.notification.org.common.model.avro.AuroQueuePayload;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;

@Mapper(componentModel = "spring")
public interface AvroBaseSchemaMapper {

    QueuePayload map (AuroQueuePayload avro);
    AuroQueuePayload map (QueuePayload payload);

    default LocaleEmbeddable mapLocale(CharSequence language) {
        if (language == null) return null;
        return LocaleEmbeddable.valueOf(language.toString());
    }

    default String mapCharSequence(CharSequence cs) {
        return cs == null ? null : cs.toString();
    }
}
