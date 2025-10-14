package scarlett.notification.org.application.presentation.crud.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scarlett.notification.org.application.presentation.crud.CrudService;
import scarlett.notification.org.application.presentation.crud.mapper.BaseMapper;
import scarlett.notification.org.application.presentation.crud.mapper.TemplateMapper;
import scarlett.notification.org.application.presentation.crud.model.TemplateModel;
import scarlett.notification.org.application.presentation.crud.service.TemplateService;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.repository.TemplateRepository;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService,
        CrudService<TemplateModel, TemplateEntity, TemplateRepository> {

    private final TemplateRepository templateRepository;
    private final TemplateMapper mapper;
    private final BeanUtils beanUtils;

    @Override
    public TemplateRepository getRepository() {
        return this.templateRepository;
    }

    @Override
    public BaseMapper<TemplateEntity, TemplateModel> getMapper() {
        return this.mapper;
    }

    @Override
    public BeanUtils getBeanUtils() {
        return this.beanUtils;
    }
}
