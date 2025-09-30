package scarlett.notification.org.main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.main.model.enums.ChannelType;
import scarlett.notification.org.main.model.enums.OrderPriority;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateModel extends BaseModel {
    private int deliveryAttempts;
    private OrderPriority orderPriority;
    private List<TemplateTranslationModel> translations;
    private List<ChannelType> allowedChannel;
}
