package scarlett.notification.org.main.sender;

import scarlett.notification.org.common.model.MessageInformation;

public interface SenderAdapter {
    void send(MessageInformation messageInformation);
}
