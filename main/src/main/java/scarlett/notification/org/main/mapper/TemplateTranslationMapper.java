package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import scarlett.notification.org.infra.model.TemplateTranslationModel;
import scarlett.notification.org.persistence.entity.TemplateTranslationEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateTranslationMapper extends BaseMapper<TemplateTranslationEntity, TemplateTranslationModel> {
    @Override
    void updateFromModel(
            TemplateTranslationModel model,
            @MappingTarget TemplateTranslationEntity entity);
}
