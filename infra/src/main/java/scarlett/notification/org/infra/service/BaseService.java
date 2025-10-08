package scarlett.notification.org.infra.service;

import scarlett.notification.org.infra.model.BaseModel;

import java.util.List;
import java.util.UUID;

public interface BaseService<MODEL extends BaseModel> {
    MODEL create(MODEL eventModel);

    MODEL update(MODEL eventModel);

    List<MODEL> getAll();

    MODEL getById(UUID id);

    void delete(UUID id);
}
