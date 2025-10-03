package scarlett.notification.org.main.service.serialization;

import scarlett.notification.org.common.model.QueuePayload;

public interface SchemaSerializer {
    byte[] serializePayload(QueuePayload payload);
}
