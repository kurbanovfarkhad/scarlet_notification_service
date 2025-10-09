package scarlett.notification.org.application.presentation.crud.service;


import scarlett.notification.org.application.presentation.crud.model.BaseModel;

import java.util.List;
import java.util.UUID;

public interface BaseService<MODEL extends BaseModel> {
    MODEL create(MODEL eventModel);

    MODEL update(MODEL eventModel);

    List<MODEL> getAll();

    MODEL getById(UUID id);

    void delete(UUID id);
}
