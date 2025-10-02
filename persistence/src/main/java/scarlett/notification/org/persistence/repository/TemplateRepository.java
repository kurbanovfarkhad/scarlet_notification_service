package scarlett.notification.org.persistence.repository;

import org.springframework.stereotype.Repository;
import scarlett.notification.org.persistence.entity.TemplateEntity;

import java.util.List;

@Repository
public interface TemplateRepository extends BaseCrudRepository<TemplateEntity> {
}
