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
import restaurant.abc.core.service.module.ReservationService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReservationTest {

    @Autowired
    ReservationService reservationService;

    @Test
    public void profileCreateTest() {

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
