package scarlett.notification.org.main.service.template;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.main.mapper.TemplateMapper;
import scarlett.notification.org.main.model.TemplateModel;
import scarlett.notification.org.persistence.entity.TemplateEntity;
import scarlett.notification.org.persistence.repository.TemplateRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateMapper mapper;
    private final BeanUtils beanUtils;

    @Override
    public List<TemplateModel> getAll() {
        return List.of();
    }

    @Override
    public TemplateModel getById(UUID templateId) {
        return templateRepository.findById(templateId).map(mapper::map).orElseThrow();
    }

    @Override
    public TemplateModel create(TemplateModel templateModel) {
        TemplateEntity map = mapper.map(templateModel);
        TemplateEntity save = templateRepository.save(map);
        return mapper.map(save);
    }

    @Override
    public TemplateModel update(TemplateModel templateModel) {
        var dbVal = templateRepository.findById(templateModel.getId())
                                   .orElseThrow(EntityNotFoundException::new);
        try {
            beanUtils.copyProperties(dbVal, templateModel);
        } catch (Exception e) {
            throw new RuntimeException("update failed", e);
        }
        return mapper.map(dbVal);
    }
}
