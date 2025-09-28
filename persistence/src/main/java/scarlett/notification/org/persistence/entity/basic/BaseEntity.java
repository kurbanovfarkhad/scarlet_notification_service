package scarlett.notification.org.persistence.entity.basic;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Entity
@Data
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    private String source;

    @CreatedBy
    private String createdBy;

}
