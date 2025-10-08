package scarlett.notification.org.application.usecase.service;

import scarlett.notification.org.application.usecase.sender.SendersChain;
import scarlett.notification.org.common.model.MessageInformation;

import java.util.List;

public interface DeliveryDispatcher {
    List<SendersChain> assembleChain(List<MessageInformation> messageInformation);
}
