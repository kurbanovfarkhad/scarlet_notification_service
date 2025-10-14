package scarlett.notification.org.application.usecase.sender.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.integration.provider.sms.SmsProvider;

@RequiredArgsConstructor
@Component("SMS")
public class SmsSenderAdapterImpl implements SenderAdapter {

    private final SmsProvider smsProvider;

    @Override
    public IntegrationResult send(MessageInformation messageInformation) {
        IntegrationResult o = new IntegrationResult();
        try {
            smsProvider.send();
            o.setResponse("something");
        } catch (Exception e) {
            o.setResponse("something");
            o.setError(e.getMessage());
        }
        return o;
    }
}
