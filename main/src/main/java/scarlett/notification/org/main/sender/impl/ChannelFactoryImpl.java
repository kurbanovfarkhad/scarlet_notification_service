package scarlett.notification.org.main.sender.impl;

import org.springframework.stereotype.Component;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.main.sender.ChannelFactory;
import scarlett.notification.org.main.sender.SenderAdapter;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ChannelFactoryImpl implements ChannelFactory {
    private final Map<ChannelType, SenderAdapter> adapters;

    public ChannelFactoryImpl(Map<String, SenderAdapter> adapters) {
        this.adapters = adapters.entrySet()
                                                     .stream()
                                                     .collect(Collectors.toMap(el -> ChannelType.valueOf(el.getKey())
                                                             , Map.Entry::getValue));
    }

    @Override
    public SenderAdapter getSenderAdapter(ChannelType channelType) {
        return adapters.get(channelType);
    }
}
