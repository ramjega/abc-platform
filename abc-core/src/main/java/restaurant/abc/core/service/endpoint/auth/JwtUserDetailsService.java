package restaurant.abc.core.service.endpoint.auth;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.repo.jpa.UserProfileRepo;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserProfileRepo profileRepo;

    public JwtUserDetailsService(ApplicationContext context) {
        this.profileRepo = context.getBean(UserProfileRepo.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserProfile> profile = profileRepo.findOneByMobile(username);

        if (profile.isPresent()) {
            return new org.springframework.security.core.userdetails.User(profile.get().getMobile(), profile.get().getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Profile not found for mobile: " + username);
        }
    }

    public UserProfile loadProfile(String mobile) throws UsernameNotFoundException {
        Optional<UserProfile> profile = profileRepo.findOneByMobile(mobile);

        if (profile.isPresent()) {
            return profile.get();
        } else {
            throw new UsernameNotFoundException("Profile not found for mobile: " + mobile);
        }
    }
}
