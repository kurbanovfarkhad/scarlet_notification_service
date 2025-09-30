package scarlett.notification.org.infra.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.infra.model.enums.ChannelType;
import scarlett.notification.org.infra.model.enums.OrderPriority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateModel extends BaseModel {
    private int deliveryAttempts;
    private OrderPriority priority;
    private List<TemplateTranslationModel> translations;
    private List<ChannelType> allowedChannel;
}
