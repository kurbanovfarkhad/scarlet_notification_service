package scarlett.notification.org.main.sender.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.MessageInformation;
import scarlett.notification.org.integration.provider.sms.SmsProvider;
import scarlett.notification.org.main.sender.IntegrationResult;
import scarlett.notification.org.main.sender.SenderAdapter;

@RequiredArgsConstructor
@Component
@Qualifier("SMS")
public class SmsSenderAdapterImpl implements SenderAdapter {

    private final SmsProvider smsProvider;

    @Override
    public IntegrationResult send(MessageInformation messageInformation) {
        IntegrationResult o = null;
        return o;
    }
}
