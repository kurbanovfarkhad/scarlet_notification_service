package scarlett.notification.org.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import scarlett.notification.org.common.model.enums.LocaleEmbeddable;

import java.util.Map;
import java.util.UUID;

@ToString
@Data
@EqualsAndHashCode
public class QueuePayload {
    private Map<String, String> properties;
    private String phoneNumber;
    private String email;
    private String applicationId;
    private LocaleEmbeddable locale;
    private String eventName;
    private UUID userId;
    // local user only
    private UUID eventId;
}
