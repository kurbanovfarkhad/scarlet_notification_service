package scarlett.notification.org.application.presentation.crud.model;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class BaseModel {
    private UUID id;
}
