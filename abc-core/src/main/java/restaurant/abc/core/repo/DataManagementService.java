package restaurant.abc.core.repo;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
@Log4j2
public class DataManagementService {
    private final UserProfileRepo profileRepo;

    public DataManagementService(ApplicationContext ctx) {
        this.profileRepo = ctx.getBean(UserProfileRepo.class);
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

    }
}
