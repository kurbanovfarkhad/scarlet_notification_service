package scarlett.notification.org.main.service;

import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.main.sender.SendersChain;

import java.util.List;

public interface DeliveryDispatcher {
    List<SendersChain> assembleChain(List<MessageInformation> messageInformation);
}
