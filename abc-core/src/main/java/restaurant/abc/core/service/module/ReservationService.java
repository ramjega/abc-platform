
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.domain.type.ReservationStatus;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.common.StatusCode;
import restaurant.abc.core.service.common.TxStatusCodes;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ReservationService extends BaseResourceService<Reservation> {

    public ReservationService(ApplicationContext ctx, JpaRepository<Reservation, Long> repo) {
        super(ctx, Reservation.class, repo);
    }

    @Override
    public Result<Reservation> create(Reservation value) {
        value.setCustomer(SecurityHolder.getProfile());
        return super.create(value);
    }

    public Result<Reservation> cancel(Reservation value) {
        Optional<Reservation> existing = repo.findById(value.getId());

        if (existing.isPresent()) {
            existing.get().setStatus(ReservationStatus.cancelled);
            return Result.of(repo.save(existing.get()));
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, type().getSimpleName() + " for id [" + value.getId() + "] not found"));
        }
    }

    public Result<Reservation> moveTo(Reservation value) {

        Optional<Reservation> existing = repo.findById(value.getId());

        if (existing.isPresent()) {
            existing.get().setStatus(value.getStatus());
            return Result.of(repo.save(existing.get()));
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, type().getSimpleName() + " for id [" + value.getId() + "] not found"));
        }
    }
}
