package scarlett.notification.org.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.Priority;

import java.util.UUID;

@ToString
@Data
@Builder
public class MessageInformation {
    private String address;
    private ChannelType channelType;
    private String body;
    private String subject;
    private Priority priority;
    private MessageInformation fallback;
    private UUID eventId;
    private int maxAttempt;
    private int remainingAttempts;

    public int decreaseAttempts() {
        return remainingAttempts--;
    }
}
