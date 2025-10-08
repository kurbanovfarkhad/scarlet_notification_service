package scarlett.notification.org.application.usecase.service;

import scarlett.notification.org.common.model.QueuePayload;

public interface ProcessingService {

    void process(QueuePayload payload);
}
