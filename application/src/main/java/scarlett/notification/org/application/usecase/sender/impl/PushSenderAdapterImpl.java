package scarlett.notification.org.application.usecase.sender.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.usecase.sender.IntegrationResult;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.integration.provider.push.PushProvider;

@RequiredArgsConstructor
@Component
@Qualifier("PUSH")
public class PushSenderAdapterImpl implements SenderAdapter {

    private final PushProvider provider;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public IntegrationResult send(MessageInformation messageInformation) {
        IntegrationResult o = null;
        provider.send();
        return o;
    }
}
