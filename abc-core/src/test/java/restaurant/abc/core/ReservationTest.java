package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.ReservationStatus;
import restaurant.abc.core.domain.type.ServiceType;
import restaurant.abc.core.service.command.CancelReservationRequest;
import restaurant.abc.core.service.command.CreateReservationRequest;
import restaurant.abc.core.service.command.ReservationInvoker;
import restaurant.abc.core.service.command.ReservationRequest;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.ReservationService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationTest {

    @Autowired
    ReservationService reservationService;

    @Test
    public void reservationCreateTest() {

        Reservation reservation = new Reservation();
        reservation.setCustomer(UserProfile.SYSTEM);
        reservation.setParticipants(4);
        reservation.setServiceType(ServiceType.dine_in);
        reservation.setDateTime("09/10/2024");

        Result<Reservation> reservationResult = reservationService.create(reservation);
        assertTrue(reservationResult.code().isSuccess());
    }

    @Test
    public void reservationCancelTest() {

        Reservation reservation = new Reservation();
        reservation.setCustomer(UserProfile.SYSTEM);
        reservation.setServiceType(ServiceType.take_out);
        reservation.setDateTime("09/10/2024");

        Result<Reservation> reservationResult = reservationService.create(reservation);
        assertTrue(reservationResult.code().isSuccess());

        Result<Reservation> cancelResult = reservationService.cancel(reservation);
        assertTrue(cancelResult.code().isSuccess());

        Optional<Reservation> reservationFound = reservationService.findById(reservationResult.value().getId());
        assertTrue(reservationFound.isPresent());
        assertEquals(ReservationStatus.cancelled, reservationFound.get().getStatus());
    }

    @Test
    public void reservationCommandTest() {

        // Reservation to create and cancel
        Reservation reservation1 = new Reservation(1L, 2, ServiceType.dine_in, ReservationStatus.initial, "2024-09-10", "", UserProfile.SYSTEM);
        Reservation reservation2 = new Reservation(2L, 2, ServiceType.take_out, ReservationStatus.initial, "2024-09-10", "", UserProfile.SYSTEM);

        // Reservation requests
        ReservationRequest createReservation1 = new CreateReservationRequest(reservationService, reservation1);
        ReservationRequest cancelReservation2 = new CancelReservationRequest(reservationService, reservation2);

        ReservationInvoker invoker = new ReservationInvoker();

        // Add request to invoker
        invoker.addCommand(createReservation1);
        invoker.addCommand(cancelReservation2);

        invoker.executeCommands();
    }
}
