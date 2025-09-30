package scarlett.notification.org.infra.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventModel extends BaseModel {
    private String name;
    private String description;
    private String schema;
    private List<TemplateModel> templates = new ArrayList<>();
}
