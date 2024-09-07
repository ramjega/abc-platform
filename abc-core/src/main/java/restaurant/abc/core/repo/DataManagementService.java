package restaurant.abc.core.repo;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.ServiceType;
import restaurant.abc.core.domain.type.UserRole;
import restaurant.abc.core.repo.jpa.ServiceRepo;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;
import restaurant.abc.core.service.factory.UserProfileFactory;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Log4j2
public class DataManagementService {
    private final UserProfileRepo profileRepo;
    private final UserProfileFactory userProfileFactory;
    private final ServiceRepo serviceRepo;

    public DataManagementService(ApplicationContext ctx) {
        this.profileRepo = ctx.getBean(UserProfileRepo.class);
        this.userProfileFactory = ctx.getBean(UserProfileFactory.class);
        this.serviceRepo = ctx.getBean(ServiceRepo.class);
    }

    @PostConstruct
    public void execute() {

        SecurityHolder.setProfile(UserProfile.SYSTEM);

        if (profileRepo.count() == 0) {
            log.debug("Creating initial users..");

            Result<UserProfile> systemUser = userProfileFactory.createSystemUser();
            if (systemUser.code().isSuccess()) {
                log.debug("Created system user");
            }

            Result<UserProfile> adminUser = userProfileFactory.createAdmin("Gowri", "0776914220", "Test@123");
            if (adminUser.code().isSuccess()) {
                log.debug("Created admin user");
            }

            Result<UserProfile> staffUser = userProfileFactory.createStaff("Jegan", "0779010066", "Test@123");
            if (staffUser.code().isSuccess()) {
                log.debug("Created staff user");
            }

            Result<UserProfile> customerUser = userProfileFactory.createCustomer("Sriram", "0775228995", "Test@123");
            if (customerUser.code().isSuccess()) {
                log.debug("Created customer user");
            }
        }

        if (serviceRepo.count() == 0) {
            log.debug("Creating initial services..");

            OfferedService dineIn = new OfferedService.Builder()
                    .setName("Dine-In")
                    .setServiceType(ServiceType.dine_in)
                    .setDescription("Enjoy a cozy atmosphere with our dine-in service, where you can savor your meals in comfort.")
                    .build();

            OfferedService privateDining = new OfferedService.Builder()
                    .setName("Private Dining")
                    .setServiceType(ServiceType.private_dine_in)
                    .setDescription("Book our private dining rooms for an exclusive and intimate experience.")
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

            OfferedService catering = new OfferedService.Builder()
                    .setName("catering")
                    .setServiceType(ServiceType.catering)
                    .setDescription("Elevate your events with our catering service, featuring a variety of dishes perfect for corporate meetings, weddings, and other special occasions.")
                    .build();

            OfferedService event = new OfferedService.Builder()
                    .setName("Event Hosting")
                    .setServiceType(ServiceType.event_hosting)
                    .setDescription("Host your events with ease, thanks to our customized packages and dedicated staff who ensure everything goes smoothly and exceeds expectations.")
                    .build();

            serviceRepo.saveAll(Arrays.asList(dineIn, privateDining, takeOut, delivery, catering, event));
        }

    }
}
