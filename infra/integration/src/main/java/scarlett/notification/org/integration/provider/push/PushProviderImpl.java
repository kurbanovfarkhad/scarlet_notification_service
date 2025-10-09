package scarlett.notification.org.integration.provider.push;

import org.springframework.stereotype.Component;

@Component
public class PushProviderImpl implements PushProvider {

    @Override
    public void send() {
        System.out.println("PushProvider send");
    }
}
