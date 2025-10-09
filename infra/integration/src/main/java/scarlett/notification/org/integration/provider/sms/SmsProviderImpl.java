package scarlett.notification.org.integration.provider.sms;

import org.springframework.stereotype.Component;

@Component
public class SmsProviderImpl implements SmsProvider {
    @Override
    public void send() {
        System.out.println("SMS Provider");
    }
}
