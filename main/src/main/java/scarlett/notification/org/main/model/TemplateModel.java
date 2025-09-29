package scarlett.notification.org.main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateModel extends BaseModel {
    private int deliveryAttempts;
}
