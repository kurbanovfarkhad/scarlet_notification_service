package scarlett.notification.org.main.mapper;

import scarlett.notification.org.infra.model.BaseModel;
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
