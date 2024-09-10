package restaurant.abc.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestBody;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.UserProfileStatus;
import restaurant.abc.core.domain.type.UserRole;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.endpoint.auth.JwtRequest;
import restaurant.abc.core.service.endpoint.rest.AuthenticationController;
import restaurant.abc.core.service.factory.UserProfileFactory;
import restaurant.abc.core.service.module.UserProfileService;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserProfileTest {

    private static final Logger log = LoggerFactory.getLogger(UserProfileTest.class);

    @Autowired
    UserProfileRepo repo;

    @Autowired
    UserProfileService profileService;

    @Autowired
    UserProfileFactory profileFactory;

    @Autowired
    AuthenticationController authController;

    @Test
    public void profileCreateTest() {

        log.debug("Clearing previous users...");
        repo.findOneByMobile("0775228995").ifPresent(userProfile -> repo.delete(userProfile));
        repo.findOneByMobile("0779010066").ifPresent(userProfile -> repo.delete(userProfile));
        repo.findOneByMobile("0776914221").ifPresent(userProfile -> repo.delete(userProfile));

        Result<UserProfile> adminUser = profileFactory.createAdmin("Gowri", "0776914221", "Test@123");
        assertTrue(adminUser.code().isSuccess());

        Result<UserProfile> staffUser = profileFactory.createStaff("Jegan", "0779010066", "Test@123");
        assertTrue(staffUser.code().isSuccess());

        Result<UserProfile> customerUser = profileFactory.createCustomer("Sriram", "0775228995", "Test@123");
        assertTrue(customerUser.code().isSuccess());
    }

    @Test
    public void invalidInputTest() {

        // clear existing user
        repo.findOneByMobile("0773456789").ifPresent(userProfile -> repo.delete(userProfile));

        // user without role
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("nimal@gmail.com");
        userProfile.setName("Nimal");
        userProfile.setMobile("0773456789");
        userProfile.setPassword("54sdf990");

        // check whether result is failure
        Result<UserProfile> result1 = profileService.create(userProfile);
        assertTrue(result1.code().isFailure());

        // user without name
        userProfile.setRole(UserRole.admin);
        userProfile.setName(null);

        // check whether result is failure
        Result<UserProfile> result2 = profileService.create(userProfile);
        assertTrue(result2.code().isFailure());

        // user without mobile
        userProfile.setName("Nimal");
        userProfile.setMobile(null);

        // check whether result is failure
        Result<UserProfile> result3 = profileService.create(userProfile);
        assertTrue(result3.code().isFailure());

        // user without password
        userProfile.setMobile("0773456789");
        userProfile.setPassword(null);

        // check whether result is failure
        Result<UserProfile> result4 = profileService.create(userProfile);
        assertTrue(result4.code().isFailure());

        // user with all attributes
        userProfile.setPassword("Test@123");

        // check whether result is success
        UserProfile result5 = profileService.create(userProfile).unwrap();
//        assertTrue(result5.code().isSuccess());
    }

    @Test
    public void profileQueryTest() {

        log.debug("Clearing previous users...");
        repo.findOneByMobile("0775228995").ifPresent(userProfile -> repo.delete(userProfile));
        repo.findOneByMobile("0779010066").ifPresent(userProfile -> repo.delete(userProfile));
        repo.findOneByMobile("0776914221").ifPresent(userProfile -> repo.delete(userProfile));
        repo.findOneByMobile("0777878789").ifPresent(userProfile -> repo.delete(userProfile));

        Result<UserProfile> adminUser = profileFactory.createAdmin("Gowri", "0776914221", "Test@123");
        assertTrue(adminUser.code().isSuccess());

        Result<UserProfile> staffUser = profileFactory.createStaff("Jegan", "0779010066", "Test@123");
        assertTrue(staffUser.code().isSuccess());

        Result<UserProfile> customerUser = profileFactory.createCustomer("Sriram", "0775228995", "Test@123");
        assertTrue(customerUser.code().isSuccess());

        Optional<UserProfile> adminFound = repo.findOneByMobile("0776914220");
        assertTrue(adminFound.isPresent());
        assertEquals("Gowri", adminFound.get().getName());

        Optional<UserProfile> staffFound = repo.findOneByMobile("0779010066");
        assertTrue(staffFound.isPresent());
        assertEquals("Jegan", staffFound.get().getName());

        Optional<UserProfile> customerFound = repo.findOneByMobile("0775228995");
        assertTrue(customerFound.isPresent());
        assertEquals("Sriram", customerFound.get().getName());

        assertFalse(repo.findOneByMobile("0777878789").isPresent());
    }

    @Test
    public void customerLoginTest() {

        // clearing previously created user
        repo.findOneByMobile("0775228995").ifPresent(userProfile -> repo.delete(userProfile));

        Result<UserProfile> customerUser = profileFactory.createCustomer("Sriram", "0775228995", "Test@123");
        assertTrue(customerUser.code().isSuccess());

        JwtRequest authenticationRequest = new JwtRequest(customerUser.value().getMobile(), "Test@123");

        // attempt one with correct mobile no and password
        ResponseEntity response1 = authController.createAuthenticationToken(authenticationRequest);

        assertEquals(200, response1.getStatusCode().value());

        authenticationRequest.setPassword("random_pwd");

        // attempt one with correct mobile no and invalid password
        ResponseEntity response2 = authController.createAuthenticationToken(authenticationRequest);

        assertEquals(401, response2.getStatusCode().value());
    }

    @Test
    public void staffLoginTest() {

        // clearing previously created user
        repo.findOneByMobile("0779010066").ifPresent(userProfile -> repo.delete(userProfile));

        Result<UserProfile> staffUser = profileFactory.createCustomer("Jegan", "0779010066", "Test@123");
        assertTrue(staffUser.code().isSuccess());

        JwtRequest authenticationRequest = new JwtRequest(staffUser.value().getMobile(), "Test@123");

        // attempt one with correct mobile no and password
        ResponseEntity response1 = authController.createAuthenticationToken(authenticationRequest);

        assertEquals(200, response1.getStatusCode().value());

        authenticationRequest.setPassword("random_pwd");

        // attempt one with correct mobile no and invalid password
        ResponseEntity response2 = authController.createAuthenticationToken(authenticationRequest);

        assertEquals(401, response2.getStatusCode().value());
    }

    @Test
    public void adminLoginTest() {

        // clearing previously created user
        repo.findOneByMobile("0776914221").ifPresent(userProfile -> repo.delete(userProfile));

        Result<UserProfile> adminUser = profileFactory.createCustomer("Gowri", "0776914221", "Test@123");
        assertTrue(adminUser.code().isSuccess());

        JwtRequest authenticationRequest = new JwtRequest(adminUser.value().getMobile(), "Test@123");

        // attempt one with correct mobile no and password
        ResponseEntity response1 = authController.createAuthenticationToken(authenticationRequest);

        assertEquals(200, response1.getStatusCode().value());

        authenticationRequest.setPassword("random_pwd");

        // attempt one with correct mobile no and invalid password
        ResponseEntity response2 = authController.createAuthenticationToken(authenticationRequest);

        assertEquals(401, response2.getStatusCode().value());
    }
}
