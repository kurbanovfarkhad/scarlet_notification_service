package scarlett.notification.org.application.usecase.sender.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.integration.provider.push.PushProvider;

@RequiredArgsConstructor
@Component("PUSH")
public class PushSenderAdapterImpl implements SenderAdapter {

    private final PushProvider provider;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public IntegrationResult send(MessageInformation messageInformation) {
        IntegrationResult o = new IntegrationResult();
        try {
            provider.send();
            o.setSuccess(Boolean.TRUE);
            o.setResponse("asdf");
        } catch (Exception e) {
            o.setSuccess(Boolean.FALSE);
            o.setResponse("asdf");
            o.setError(e.getMessage());
        }

        return o;
    }
}
