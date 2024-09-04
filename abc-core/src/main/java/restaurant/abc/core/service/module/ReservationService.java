
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.Reservation;

@org.springframework.stereotype.Service
public class ReservationService extends BaseResourceService<Reservation> {

    public ReservationService(ApplicationContext ctx, JpaRepository<Reservation, Long> repo) {
        super(ctx, Reservation.class, repo);
    }
}
