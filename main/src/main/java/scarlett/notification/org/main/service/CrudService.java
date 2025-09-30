package scarlett.notification.org.main.service;

import jakarta.persistence.EntityNotFoundException;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.infra.model.BaseModel;
import scarlett.notification.org.infra.service.BaseService;
import scarlett.notification.org.main.mapper.BaseMapper;
import scarlett.notification.org.persistence.entity.basic.BaseEntity;
import scarlett.notification.org.persistence.repository.BaseCrudRepository;

import java.util.List;
import java.util.UUID;

public interface CrudService<
        MODEL extends BaseModel, ENTITY extends BaseEntity,
        REPOSITORY extends BaseCrudRepository<ENTITY>
        > extends BaseService<MODEL> {
    REPOSITORY getRepository();

    BaseMapper<ENTITY, MODEL> getMapper();

    BeanUtils getBeanUtils();

    @Override
    default void delete(UUID id) {
        getRepository().deleteById(id);
    }

    @Override
    default MODEL create(MODEL eventModel) {
        ENTITY map = getMapper().map(eventModel);
        ENTITY save = getRepository().save(map);
        return getMapper().map(save);
    }

    @Override
    default MODEL update(MODEL eventModel) {
        var dbVal = getRepository().findById(eventModel.getId())
                                   .orElseThrow(EntityNotFoundException::new);
        try {
            getBeanUtils().copyProperties(dbVal, eventModel);
        } catch (Exception e) {
            throw new RuntimeException("update failed", e);
        }
        return getMapper().map(dbVal);
    }

    @Override
    default List<MODEL> getAll() {
        return getMapper().map(getRepository().findAll());
    }

    @Override
    default MODEL getById(UUID id) {
        return getRepository().findById(id).map(getMapper()::map).orElseThrow(EntityNotFoundException::new);
    }
}
