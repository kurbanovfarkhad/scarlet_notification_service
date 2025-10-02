package scarlett.notification.org.common.model;

import lombok.Builder;
import scarlett.notification.org.common.model.enums.ChannelType;

@Builder
public class MessageInformation {
    private String address;
    private ChannelType channelType;
    private String body;
    private String subject;
}
