package scarlett.notification.org.infra.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.ChannelType;
import scarlett.notification.org.common.model.enums.Priority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateModel extends BaseModel {
    private int deliveryAttempts;
    private List<TemplateTranslationModel> translations;
    private List<ChannelType> allowedChannel;
    private ChannelType defaultChannel;
    private Priority priority;
}
