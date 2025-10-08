package scarlett.notification.org.main.sender.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.integration.provider.push.PushProvider;
import scarlett.notification.org.main.sender.IntegrationResult;
import scarlett.notification.org.main.sender.SenderAdapter;

@Component
@Qualifier("PUSH")
public class PushSenderAdapterImpl implements SenderAdapter {

    private PushProvider provider;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public IntegrationResult send(MessageInformation messageInformation) {
        IntegrationResult o = null;

        return o;
    }
}
