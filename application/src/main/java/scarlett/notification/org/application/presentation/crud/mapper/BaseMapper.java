package scarlett.notification.org.application.presentation.crud.mapper;

import scarlett.notification.org.application.presentation.crud.model.BaseModel;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<ENTITY extends BaseEntity, MODEL extends BaseModel> {
    ENTITY map(MODEL model);

    MODEL map(ENTITY entity);

    default List<MODEL> map(List<ENTITY> entities) {
        return entities.stream()
                       .map(this::map)
                       .collect(Collectors.toList());
    }
}
