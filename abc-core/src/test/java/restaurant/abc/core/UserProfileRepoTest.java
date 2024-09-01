package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.UserRole;
import restaurant.abc.core.domain.type.UserProfileStatus;
import restaurant.abc.core.repo.jpa.UserProfileRepo;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserProfileRepoTest {

    @Autowired
    UserProfileRepo userProfileRepo;

    @Test
    public void createUser() {
        UserProfile userProfile = new UserProfile();
        userProfile.setMobile("0775228995");
        userProfile.setPassword("Test@123");
        userProfile.setName("Sriram");
        userProfile.setRole(UserRole.admin);
        userProfile.setStatus(UserProfileStatus.active);

        userProfileRepo.save(userProfile);

        assertTrue(userProfileRepo.findOneByMobile("0775228995").isPresent());
    }
}
