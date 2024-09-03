package restaurant.abc.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "name"),
})
public class OfferedService extends ResourceEntity {

    @Id
    @GeneratedValue(generator = "service_id_generator")
    protected Long id;

    private String name;

    private String description;

    private String price;
}
