package restaurant.abc.core.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import restaurant.abc.core.domain.type.ReservationStatus;
import restaurant.abc.core.domain.type.ServiceType;

import javax.persistence.*;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
})
public class Query extends ResourceEntity {

    @Id
    @GeneratedValue(generator = "query_id_generator")
    protected Long id;

    private Long reservationId;

    private String query;

    private String response;
}
