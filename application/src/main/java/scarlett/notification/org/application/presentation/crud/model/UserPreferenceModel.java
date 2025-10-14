package scarlett.notification.org.application.presentation.crud.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.ChannelType;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPreferenceModel extends BaseModel {
    private ChannelType defaultChannelType;
    private UUID userId;
}
