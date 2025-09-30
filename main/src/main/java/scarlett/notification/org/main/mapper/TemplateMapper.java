package scarlett.notification.org.main.mapper;

import org.mapstruct.Mapper;
import scarlett.notification.org.infra.model.TemplateModel;
import scarlett.notification.org.persistence.entity.TemplateEntity;

@Mapper(componentModel = "spring", uses = {TemplateTranslationMapper.class})
public interface TemplateMapper extends BaseMapper<TemplateEntity, TemplateModel>{}
