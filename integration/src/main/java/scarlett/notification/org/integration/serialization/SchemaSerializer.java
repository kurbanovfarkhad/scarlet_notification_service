package scarlett.notification.org.integration.serialization;

import scarlett.notification.org.common.model.QueuePayload;

public interface SchemaSerializer {
    byte[] serialize(QueuePayload payload);

    QueuePayload deserialize(byte[] payload);
}
