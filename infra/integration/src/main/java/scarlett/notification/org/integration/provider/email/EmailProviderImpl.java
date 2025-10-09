package scarlett.notification.org.integration.provider.email;

public class EmailProviderImpl implements EmailProvider {
    @Override
    public void send() {
        System.out.println("EmailProvider send");
    }
}
