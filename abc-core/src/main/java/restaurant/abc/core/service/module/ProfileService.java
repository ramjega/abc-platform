package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.domain.type.UserProfileStatus;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.common.StatusCode;
import restaurant.abc.core.service.common.TxStatusCodes;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ProfileService extends BaseResourceService<UserProfile> {
    private final UserProfileRepo userRepo;

    public ProfileService(ApplicationContext ctx, JpaRepository<UserProfile, Long> repo) {
        super(ctx, UserProfile.class, repo);
        this.userRepo = ctx.getBean(UserProfileRepo.class);
    }

    @Override
    public Result<UserProfile> create(UserProfile value) {

        if (value.getMobile() == null || value.getPassword() == null) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_VALIDATION_FAILED, "Missing required fields - mobile | password"));
        }

        if (!value.getMobile().startsWith("0") || value.getMobile().length() != 10) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_VALIDATION_FAILED, "Mobile number should start with 0 and must contain 10 numbers"));
        }

        if (userRepo.findOneByMobile(value.getMobile()).isPresent()) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_ALREADY_EXIST, "Profile for mobile [" + value.getMobile() + "] is already exist!"));
        }

        return super.create(value);
    }

    public Result<UserProfile> complete(UserProfile value) {

        if (isNull(value.getRole()) || isNull(value.getName()) || isNull(value.getEmail()) || isNull(value.getAddress())) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_VALIDATION_FAILED, "Missing required fields - type | name | email | address"));
        }

        Optional<UserProfile> profile = userRepo.findById(SecurityHolder.getProfileId());

        if (!profile.isPresent()) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, "Request profile not found!"));
        }

        profile.get().setRole(value.getRole());
        profile.get().setName(value.getName());
        profile.get().setEmail(value.getEmail());
        profile.get().setAddress(value.getEmail());

        return super.update(profile.get());
    }

    @Override
    public Result<UserProfile> update(UserProfile value) {

        if (value.getMobile() == null || value.getPassword() == null) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_VALIDATION_FAILED, "Missing required fields - mobile | password"));
        }

        return super.update(value);
    }

    public Result<UserProfile> suspend(Long id) {
        Optional<UserProfile> profile = userRepo.findById(id);
        if (profile.isPresent()) {
            profile.get().setStatus(UserProfileStatus.suspended);
            return super.update(profile.get());
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, "Profile not found for id [" + id + "]"));
        }
    }

    public Result<UserProfile> activate(Long id) {
        Optional<UserProfile> profile = userRepo.findById(id);
        if (profile.isPresent()) {
            profile.get().setStatus(UserProfileStatus.active);
            return super.update(profile.get());
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, "Profile not found for id [" + id + "]"));
        }
    }

    public Result<UserProfile> delete(Long id) {
        Optional<UserProfile> profile = userRepo.findById(id);
        if (profile.isPresent()) {
            return super.delete(profile.get().getId());
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, "Profile not found for id [" + id + "]"));
        }
    }
}
