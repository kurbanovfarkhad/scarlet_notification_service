package scarlett.notification.org.infra.model;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class BaseModel {
    private UUID id;
}
