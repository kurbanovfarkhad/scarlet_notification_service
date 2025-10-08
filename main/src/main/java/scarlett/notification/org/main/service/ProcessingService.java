package scarlett.notification.org.main.service;

import scarlett.notification.org.common.model.QueuePayload;

public interface ProcessingService {

    void process(QueuePayload payload);
}
