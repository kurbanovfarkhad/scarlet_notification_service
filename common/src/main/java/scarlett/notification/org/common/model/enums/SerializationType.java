package scarlett.notification.org.common.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SerializationType {
    AVRO,
    PROTOBUF,
    ;

    @JsonCreator
    public SerializationType fromJson(String value) {
        return SerializationType.valueOf(value.toUpperCase());
    }
}
