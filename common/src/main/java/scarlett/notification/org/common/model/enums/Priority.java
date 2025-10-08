package scarlett.notification.org.common.model.enums;

import lombok.Getter;

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
}
