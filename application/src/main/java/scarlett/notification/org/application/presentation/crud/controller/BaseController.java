package scarlett.notification.org.application.presentation.crud.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import scarlett.notification.org.application.presentation.crud.model.BaseModel;
import scarlett.notification.org.application.presentation.crud.service.BaseService;

import java.util.List;
import java.util.UUID;

public interface BaseController<MODEL extends BaseModel, SERVICE extends BaseService<MODEL>> {

    SERVICE getService();

    @GetMapping
    default List<MODEL> getAll() {
        return getService().getAll();
    }

    @GetMapping("/{id}")
    default MODEL getById(@PathVariable UUID id) {
        return getService().getById(id);
    }

    @PostMapping
    default MODEL create(@RequestBody MODEL model) {
        return getService().create(model);
    }

    @PatchMapping
    default MODEL update(@RequestBody MODEL model) {
        return getService().update(model);
    }

    @DeleteMapping("/{id}")
    default void delete(@PathVariable UUID id) {
        getService().delete(id);
    }
}
