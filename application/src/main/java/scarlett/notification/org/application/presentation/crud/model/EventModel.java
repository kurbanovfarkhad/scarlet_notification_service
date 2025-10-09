package scarlett.notification.org.application.presentation.crud.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import scarlett.notification.org.common.model.enums.SerializationType;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventModel extends BaseModel {
    private String name;
    private String description;
    private SerializationType schemaType;
}
