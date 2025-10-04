package scarlett.notification.org.common.model;

import lombok.Builder;
import lombok.Data;
import scarlett.notification.org.common.model.enums.ChannelType;

@Data
@Builder
public class MessageInformation {
    private String address;
    private ChannelType channelType;
    private String body;
    private String subject;

    private MessageInformation fallback;
}
