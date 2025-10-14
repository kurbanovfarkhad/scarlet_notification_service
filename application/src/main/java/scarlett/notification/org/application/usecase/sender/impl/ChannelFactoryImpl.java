package scarlett.notification.org.application.usecase.sender.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scarlett.notification.org.application.usecase.sender.ChannelFactory;
import scarlett.notification.org.application.usecase.sender.SenderAdapter;
import scarlett.notification.org.common.model.enums.ChannelType;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ChannelFactoryImpl implements ChannelFactory {
    private final Map<String, SenderAdapter> adapters;

    @Override
    public SenderAdapter getSenderAdapter(ChannelType channelType) {
        SenderAdapter senderAdapter = adapters.get(channelType.name());
        if (senderAdapter == null) {
            throw new IllegalArgumentException("unknown channel type: " + channelType.name());
        }
        return senderAdapter;
    }
}
