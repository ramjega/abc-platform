package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.Query;
import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.ServiceType;
import restaurant.abc.core.repo.jpa.QueryRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.QueryService;
import restaurant.abc.core.service.module.ReservationService;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerQueryTest {

    @Autowired
    QueryRepo repo;

    @Autowired
    QueryService queryService;

    @Autowired
    ReservationService reservationService;

    @Test
    public void queryWorkflowTest() {
        // make a reservation for take out
        Reservation reservation = new Reservation();
        reservation.setCustomer(UserProfile.SYSTEM);
        reservation.setServiceType(ServiceType.take_out);
        reservation.setDateTime("09/10/2024");

        Result<Reservation> reservationResult = reservationService.create(reservation);
        assertTrue(reservationResult.code().isSuccess());

        // submitting a query since no status updates
        Query customerQuery = new Query();
        customerQuery.setQuery("Hi, Is this item ready for collecting ?");
        customerQuery.setReservationId(reservationResult.value().getId());

        Result<Query> queryResult = queryService.create(customerQuery);
        assertTrue(queryResult.code().isSuccess());

        // staff responds to the query
        Query response = new Query();
        response.setResponse("Yes, you can collect it");

        Result<Query> replyResult = queryService.reply(queryResult.value().getId(), response);
        assertTrue(replyResult.code().isSuccess());

        // check whether the data has query and response
        Optional<Query> queryFound = repo.findById(queryResult.unwrap().getId());
        assertTrue(queryFound.isPresent());
        assertNotNull(queryFound.get().getQuery());
        assertNotNull(queryFound.get().getResponse());
    }

}
