package restaurant.abc.core.service.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.UserProfileStatus;
import restaurant.abc.core.domain.type.UserRole;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.UserProfileService;

@Service
public class UserProfileFactory {
    private final UserProfileService profileService;

    public UserProfileFactory(ApplicationContext context) {
        this.profileService = context.getBean(UserProfileService.class);
    }

    public Result<UserProfile> createCustomer(String name, String mobile, String password) {
        return createUserProfile(name, mobile, password, UserRole.customer);
    }

    public Result<UserProfile> createStaff(String name, String mobile, String password) {
        return createUserProfile(name, mobile, password, UserRole.staff);
    }

    public Result<UserProfile> createAdmin(String name, String mobile, String password) {
        return createUserProfile(name, mobile, password, UserRole.admin);
    }

    public Result<UserProfile> createSystemUser() {
        return profileService.create(UserProfile.SYSTEM);
    }

    private Result<UserProfile> createUserProfile(String name, String mobile, String password, UserRole admin) {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(name);
        userProfile.setMobile(mobile);
        userProfile.setPassword(password);
        userProfile.setRole(admin);
        userProfile.setStatus(UserProfileStatus.active);
        return profileService.create(userProfile);
    }
}


