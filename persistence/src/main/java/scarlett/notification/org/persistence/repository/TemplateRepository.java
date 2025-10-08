package scarlett.notification.org.persistence.repository;

import org.springframework.stereotype.Repository;
import scarlett.notification.org.persistence.entity.TemplateEntity;

@Repository
public interface TemplateRepository extends BaseCrudRepository<TemplateEntity> {
}
