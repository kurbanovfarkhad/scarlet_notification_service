package scarlett.notification.org.application.crud.mapper;

import org.mapstruct.MappingTarget;
import scarlett.notification.org.application.crud.model.BaseModel;
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

    void updateFromModel(
            MODEL model,
            @MappingTarget ENTITY entity);
}
