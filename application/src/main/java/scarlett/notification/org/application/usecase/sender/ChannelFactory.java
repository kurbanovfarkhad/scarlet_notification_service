package scarlett.notification.org.application.usecase.sender;

import scarlett.notification.org.common.model.enums.ChannelType;

public interface ChannelFactory {
    SenderAdapter getSenderAdapter(ChannelType channelType);
}
