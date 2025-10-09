package scarlett.notification.org.application.presentation.crud.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.Priority;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateModel extends BaseModel {
    private int deliveryAttempts;
    private List<TemplateTranslationModel> translations;
    private List<ChannelType> allowedChannel;
    private ChannelType defaultChannel;
    private Priority priority;
    private List<UUID> events;
}
