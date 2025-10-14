package scarlett.notification.org.application.usecase.sender.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import scarlett.notification.org.application.usecase.IntegrationResult;
import scarlett.notification.org.application.usecase.sender.ChannelFactory;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.application.usecase.service.NotificationService;
import scarlett.notification.org.common.model.MessageInformation;


@Component("proxySender")
@Primary
public class ProxySenderAdapter implements SenderAdapter {
    private final NotificationService notificationService;
    private final ChannelFactory channelFactory;

    public ProxySenderAdapter(
            NotificationService notificationService,
            @Lazy ChannelFactory channelFactory) {
        this.notificationService = notificationService;
        this.channelFactory = channelFactory;
    }

    @Override
    public IntegrationResult send(MessageInformation messageInformation) {
        SenderAdapter senderAdapter = channelFactory.getSenderAdapter(messageInformation.getChannelType());
        IntegrationResult send = senderAdapter.send(messageInformation);
        notificationService.recordingAttempt(messageInformation, send);
        return send;
    }

}
