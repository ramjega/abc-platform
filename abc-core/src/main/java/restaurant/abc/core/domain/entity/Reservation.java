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
        @Index(columnList = "status")
})
public class Reservation extends ResourceEntity {

    @Id
    @GeneratedValue(generator = "reservation_id_generator")
    protected Long id;

    private Integer participants;

    private ServiceType serviceType;

    private ReservationStatus status = ReservationStatus.initial;

    private String dateTime;

    private String description;
}
