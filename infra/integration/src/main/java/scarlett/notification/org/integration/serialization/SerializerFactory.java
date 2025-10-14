package scarlett.notification.org.integration.serialization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.enums.SerializationType;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class SerializerFactory {
    private final Map<String, SchemaSerializer> serializers;

    public SchemaSerializer getSerializer(SerializationType serializationType) {
        return serializers.get(serializationType.name());
    }
}
