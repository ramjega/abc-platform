package restaurant.abc.core.domain.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public abstract class ResourceEntity implements Serializable {

    public abstract Long getId();

    // pre-update
    protected Timestamp createdTime;

    protected Timestamp modifiedTime;

    protected Long createdByUserId;

    protected Long modifiedByUserId;

    @PrePersist
    private void setMetaFieldsOnCreate() {
        this.createdTime = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void setMetaFieldsOnUpdate() {
        this.modifiedTime = new Timestamp(System.currentTimeMillis());
    }

}
