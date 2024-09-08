package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.ServiceType;
import restaurant.abc.core.repo.jpa.ServiceRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.endpoint.auth.JwtRequest;
import restaurant.abc.core.service.module.ServiceProvider;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OfferedServiceTest {

    @Autowired
    ServiceRepo repo;

    @Autowired
    ServiceProvider serviceProvider;

    @Test
    public void basicServiceTest() {

        // clearing all existing services
        repo.deleteAll();

        // creating 3 basic services

        OfferedService dineIn = new OfferedService.Builder()
                .setName("Dine-In")
                .setServiceType(ServiceType.dine_in)
                .setDescription("Enjoy a cozy atmosphere with our dine-in service, where you can savor your meals in comfort.")
                .build();

        OfferedService takeOut = new OfferedService.Builder()
                .setName("Takeout")
                .setServiceType(ServiceType.take_out)
                .setDescription("Order your favorite dishes for pickup, perfectly prepared and ready to enjoy at home.")
                .build();

        OfferedService delivery = new OfferedService.Builder()
                .setName("Delivery")
                .setServiceType(ServiceType.delivery)
                .setDescription("Have our delicious meals delivered straight to your doorstep, ensuring you enjoy convenience and freshness with every bite.")
                .build();

        Result<OfferedService> result1 = serviceProvider.create(dineIn);
        Result<OfferedService> result2 = serviceProvider.create(takeOut);
        Result<OfferedService> result3 = serviceProvider.create(delivery);

        // validating the result successfulness
        assertTrue(result1.code().isSuccess());
        assertTrue(result2.code().isSuccess());
        assertTrue(result3.code().isSuccess());

        // query created services
        List<OfferedService> services = serviceProvider.query();
        assertEquals(3, services.size());
    }

    @Test
    public void additionalServiceTest() {

        // clearing all existing services
        repo.deleteAll();

        // creating 2 additional services

        OfferedService liveMusicService = new OfferedService.Builder()
                .setName("Live Music")
                .setDescription("Enjoy live music")
                .setServiceType(ServiceType.additional)
                .setPrice(1500)
                .markAsAdditional()
                .build();

        OfferedService tableDecorationService = new OfferedService.Builder()
                .setName("Table Decoration")
                .setDescription("Beautiful table decorations")
                .setServiceType(ServiceType.additional)
                .setPrice(500)
                .markAsAdditional()
                .build();

        Result<OfferedService> result1 = serviceProvider.create(liveMusicService);
        Result<OfferedService> result2 = serviceProvider.create(tableDecorationService);

        // validating the result successfulness
        assertTrue(result1.code().isSuccess());
        assertTrue(result2.code().isSuccess());

        // query create services
        List<OfferedService> services = serviceProvider.query();
        assertEquals(2, services.size());
    }

}
