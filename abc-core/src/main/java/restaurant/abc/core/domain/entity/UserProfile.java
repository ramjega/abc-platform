package restaurant.abc.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import restaurant.abc.core.domain.type.UserRole;
import restaurant.abc.core.domain.type.UserProfileStatus;

import javax.persistence.*;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {
        @Index(columnList = "mobile"),
        @Index(columnList = "status"),
        @Index(columnList = "role")
})
public class UserProfile extends ResourceEntity {

    @JsonIgnore
    public static final UserProfile SYSTEM;

    static {
        SYSTEM = new UserProfile();
        SYSTEM.setId(1L);
        SYSTEM.setMobile("mobile");
        SYSTEM.setPassword("password");
        SYSTEM.setRole(UserRole.admin);
        SYSTEM.setStatus(UserProfileStatus.active);
    }

    @Id
    @GeneratedValue(generator = "user_id_generator")
    protected Long id;

    private String mobile;

    private String password;

    private String name;

    private String email;

    private String address;

    private UserProfileStatus status = UserProfileStatus.active;

    private UserRole role;

    @PrePersist
    private void hasPassword() {
        this.password = new BCryptPasswordEncoder().encode(this.password);
    }

    private String token;
}
