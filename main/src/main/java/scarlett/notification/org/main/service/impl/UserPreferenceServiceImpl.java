package scarlett.notification.org.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scarlett.notification.org.common.BeanUtils;
import scarlett.notification.org.infra.model.UserPreferenceModel;
import scarlett.notification.org.infra.service.UserPreferenceService;
import scarlett.notification.org.main.mapper.BaseMapper;
import scarlett.notification.org.main.mapper.UserPreferenceMapper;
import scarlett.notification.org.main.service.CrudService;
import scarlett.notification.org.persistence.entity.UserPreferenceEntity;
import scarlett.notification.org.persistence.repository.UserPreferenceRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserPreferenceServiceImpl implements UserPreferenceService,
        CrudService<UserPreferenceModel, UserPreferenceEntity, UserPreferenceRepository> {
    private final UserPreferenceRepository userPreferenceRepository;
    private final BeanUtils beanUtils;
    private final UserPreferenceMapper mapper;

    @Override
    public UserPreferenceRepository getRepository() {
        return this.userPreferenceRepository;
    }

    @Override
    public BaseMapper<UserPreferenceEntity, UserPreferenceModel> getMapper() {
        return this.mapper;
    }

    @Override
    public BeanUtils getBeanUtils() {
        return this.beanUtils;
    }

    @Override
    public UserPreferenceModel getUserPreferences(UUID userId) {

        return userPreferenceRepository.findByUserId(userId).map(mapper::map).orElse(null);
    }
}
