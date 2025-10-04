package scarlett.notification.org.main.sender.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.main.sender.SenderAdapter;

@Component
@Qualifier("SMS")
public class SmsSenderAdapterImpl implements SenderAdapter {

    @Override
    public void send(MessageInformation messageInformation) {

    }
}
