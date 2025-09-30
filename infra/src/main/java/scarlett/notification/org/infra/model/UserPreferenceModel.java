package scarlett.notification.org.infra.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.infra.model.enums.ChannelType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPreferenceModel extends BaseModel {
    private ChannelType defaultChannelType;
    private UUID userId;
}
