package scarlett.notification.org.application.usecase.service;

import scarlett.notification.org.application.usecase.sender.SendersChain;
import scarlett.notification.org.common.model.QueuePayload;

import java.util.List;

public interface ProcessingService {

    List<SendersChain> process(QueuePayload payload);
}
