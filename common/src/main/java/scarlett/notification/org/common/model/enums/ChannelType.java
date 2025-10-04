package scarlett.notification.org.common.model.enums;

import lombok.Getter;

@Getter
public enum ChannelType {
    SMS(null),
    PUSH(SMS),
    EMAIL(null),
    ;
    private final ChannelType fallbackChannel;

    ChannelType(ChannelType channelType) {
        this.fallbackChannel = channelType;
    }

}
