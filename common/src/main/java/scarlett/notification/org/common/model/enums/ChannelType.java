package scarlett.notification.org.common.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonCreator
    public ChannelType fromString(String type) {
        return ChannelType.valueOf(type);
    }

    @JsonValue
    public String toJson() {
        return name();
    }

}
