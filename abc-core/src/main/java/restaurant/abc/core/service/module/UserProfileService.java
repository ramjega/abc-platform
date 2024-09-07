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

import java.util.Optional;

@Service
public class UserProfileService extends BaseResourceService<UserProfile> {
    private final UserProfileRepo userRepo;

    public UserProfileService(ApplicationContext ctx, JpaRepository<UserProfile, Long> repo) {
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

    @Override
    public Result<UserProfile> update(UserProfile value) {

        if (value.getName() == null || value.getMobile() == null || value.getEmail() == null || value.getRole() == null) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_VALIDATION_FAILED, "Missing required fields - name | mobile | email | role"));
        }

        Optional<UserProfile> existing = repo.findById(value.getId());

        if (existing.isPresent()) {
            existing.get().setName(value.getName());
            existing.get().setMobile(value.getMobile());
            existing.get().setEmail(value.getEmail());
            existing.get().setRole(value.getRole());

            return Result.of(repo.save(existing.get()));
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, type().getSimpleName() + " for id [" + value.getId() + "] not found"));
        }
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
