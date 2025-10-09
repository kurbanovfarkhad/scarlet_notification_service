package scarlett.notification.org.common.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Priority {
    HIGH(0),
    MEDIUM(100),
    LOW(1000),
    ;
    private final int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    @JsonValue
    public int getPriority() {
        return priority;
    }

    @JsonCreator
    public Priority fromValue(int priority) {
        return Arrays.stream(Priority.values())
                     .filter(value -> value.getPriority() == priority)
                     .findFirst()
                     .orElseThrow();
    }
}
