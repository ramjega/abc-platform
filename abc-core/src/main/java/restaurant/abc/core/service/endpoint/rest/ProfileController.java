package restaurant.abc.core.service.endpoint.rest;


import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.abc.core.domain.entity.UserProfile;
import restaurant.abc.core.repo.jpa.UserProfileRepo;
import restaurant.abc.core.service.common.ResourcePatch;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.common.StatusCode;
import restaurant.abc.core.service.common.TxStatusCodes;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;
import restaurant.abc.core.service.module.UserProfileService;

import java.util.List;
import java.util.Optional;

import static restaurant.abc.core.service.common.Converter.response;


@RestController
@CrossOrigin
public class ProfileController {

    private final UserProfileService service;
    private final UserProfileRepo repo;

    public ProfileController(ApplicationContext context) {
        this.service = context.getBean(UserProfileService.class);
        this.repo = context.getBean(UserProfileRepo.class);
    }

    @GetMapping(value = "/profile/get")
    public ResponseEntity get() {
        Optional<UserProfile> profile = repo.findById(SecurityHolder.getProfileId());

        if (profile.isPresent()) {
            return response(Result.of(profile.get()));
        } else {
            return response(Result.of(StatusCode.sc(TxStatusCodes.SC_VALIDATION_FAILED, "Profile not found for id [" + SecurityHolder.getProfileId() + "]")));
        }
    }

    @PostMapping(value = "/profile/register")
    public ResponseEntity create(@RequestBody UserProfile value) {
        return response(service.create(value));
    }

    @GetMapping(value = "/profile/fetch")
    public ResponseEntity fetch() {
        return response(Result.of(service.query()));
    }

    @PutMapping(value = "/profile/update")
    public ResponseEntity update(@RequestBody UserProfile value) {
        return response(service.update(value));
    }

    @PostMapping(value = "/profile/suspend/{id}")
    public ResponseEntity suspend(@PathVariable Long id) {
        return response(service.suspend(id));
    }

    @PostMapping(value = "/profile/activate/{id}")
    public ResponseEntity activate(@PathVariable Long id) {
        return response(service.activate(id));
    }

    @DeleteMapping(value = "/profile/remove/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return response(Result.of(service.delete(id)));
    }
}
