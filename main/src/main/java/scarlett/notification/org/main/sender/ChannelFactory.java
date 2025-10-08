package scarlett.notification.org.main.sender;

import scarlett.notification.org.common.model.enums.ChannelType;

public interface ChannelFactory {
    SenderAdapter getSenderAdapter(ChannelType channelType);
}
