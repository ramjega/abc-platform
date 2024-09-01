package restaurant.abc.core.service.endpoint.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import restaurant.abc.core.domain.entity.UserProfile;

import java.util.ArrayList;
import java.util.Optional;

public class SecurityHolder {

    public static UserProfile getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserProfile) {
            return ((UserProfile) principal);
        } else {
            return null;
        }
    }

    public static Long getProfileId() {
        return Optional.ofNullable(getProfile()).map(UserProfile::getId).orElse(null);
    }

    public static void setProfile(UserProfile profile) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                profile, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
