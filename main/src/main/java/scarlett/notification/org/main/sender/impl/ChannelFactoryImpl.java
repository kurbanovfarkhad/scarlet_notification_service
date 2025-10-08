package scarlett.notification.org.main.sender.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.main.sender.ChannelFactory;
import scarlett.notification.org.main.sender.DecoratedSenderAdapter;
import scarlett.notification.org.main.sender.SenderAdapter;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ChannelFactoryImpl implements ChannelFactory {
    private final Map<String, SenderAdapter> adapters;
    private final DecoratedSenderAdapter decoratedSenderAdapter;

    @Override
    public SenderAdapter getSenderAdapter(ChannelType channelType) {
        SenderAdapter senderAdapter = adapters.get(channelType.name());
        return messageInformation -> decoratedSenderAdapter.send(messageInformation, senderAdapter);
    }
}
