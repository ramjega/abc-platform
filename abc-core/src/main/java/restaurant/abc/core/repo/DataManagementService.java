package restaurant.abc.core.repo;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.repo.jpa.ServiceRepo;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Service
@Log4j2
public class DataManagementService {
    private final UserProfileRepo profileRepo;
    private final ServiceRepo serviceRepo;

    public DataManagementService(ApplicationContext ctx) {
        this.profileRepo = ctx.getBean(UserProfileRepo.class);
        this.serviceRepo = ctx.getBean(ServiceRepo.class);
    }

    @PostConstruct
    public void execute() {

        // create initial user
        SecurityHolder.setProfile(UserProfile.SYSTEM);

        if (profileRepo.count() == 0) {
            log.debug("Creating initial users..");

            UserProfile system = UserProfile.SYSTEM;

            profileRepo.saveAll(Collections.singletonList(system));
        }

        serviceRepo.deleteAll();

        if (serviceRepo.count() == 0) {
            OfferedService dineIn = new OfferedService();
            dineIn.setName("Dine-In");
            dineIn.setDescription("Enjoy a cozy atmosphere with our dine-in service, where you can savor your meals in comfort and receive attentive service.");

            OfferedService privateDining = new OfferedService();
            privateDining.setName("Private Dining");
            privateDining.setDescription("Book our private dining rooms for an exclusive and intimate experience, ideal for special celebrations and important meetings.");

            OfferedService takeOut = new OfferedService();
            takeOut.setName("Takeout");
            takeOut.setDescription("Order your favorite dishes for pickup, perfectly prepared and ready to enjoy at home or on the go, providing convenience and taste.");

            OfferedService delivery = new OfferedService();
            delivery.setName("Delivery");
            delivery.setDescription("Have our delicious meals delivered straight to your doorstep, ensuring you enjoy convenience and freshness with every bite.");

            OfferedService catering = new OfferedService();
            catering.setName("Catering");
            catering.setDescription("Elevate your events with our catering service, featuring a variety of dishes perfect for corporate meetings, weddings, and other special occasions.");

            OfferedService event = new OfferedService();
            event.setName("Event Hosting");
            event.setDescription("Host your events with ease, thanks to our customized packages and dedicated staff who ensure everything goes smoothly and exceeds expectations.");

            OfferedService support = new OfferedService();
            support.setName("24/7 Customer Support");
            support.setDescription("Our dedicated team is available around the clock to assist with reservations, orders, and any inquiries you may have, ensuring excellent service at all times.");

            serviceRepo.saveAll(Arrays.asList(dineIn, privateDining, takeOut, delivery, catering, event, support));
        }

    }
}
