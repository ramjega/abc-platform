package restaurant.abc.core.service.command;

import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.service.module.ReservationService;

public class CreateReservationRequest implements ReservationRequest {
    private ReservationService reservationService;
    private Reservation reservation;

    public CreateReservationRequest(ReservationService reservationService, Reservation reservation) {
        this.reservationService = reservationService;
        this.reservation = reservation;
    }

    @Override
    public void execute() {
        reservationService.create(reservation);
    }
}