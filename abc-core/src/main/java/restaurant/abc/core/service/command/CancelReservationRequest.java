package restaurant.abc.core.service.command;

import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.service.module.ReservationService;

public class CancelReservationRequest implements ReservationRequest {
    private ReservationService reservationService;
    private Reservation reservation;

    public CancelReservationRequest(ReservationService reservationService, Reservation reservation) {
        this.reservationService = reservationService;
        this.reservation = reservation;
    }

    @Override
    public void execute() {
        reservationService.cancel(reservation);
    }
}