package restaurant.abc.core.service.endpoint.rest;


import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.abc.core.domain.entity.Reservation;
import restaurant.abc.core.domain.type.ReservationStatus;
import restaurant.abc.core.repo.jpa.ReservationRepo;
import restaurant.abc.core.service.common.ResourcePatch;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.endpoint.auth.SecurityHolder;
import restaurant.abc.core.service.module.ReservationService;

import java.util.List;

import static restaurant.abc.core.service.common.Converter.response;


@RestController
@CrossOrigin
public class ReservationController {
    private final ReservationService service;
    private final ReservationRepo repo;

    public ReservationController(ApplicationContext context) {
        this.service = context.getBean(ReservationService.class);
        this.repo = context.getBean(ReservationRepo.class);
    }

    @GetMapping(value = "/reservation/fetch")
    public ResponseEntity fetch() {
        return response(Result.of(service.query()));
    }

    @GetMapping(value = "/reservation/fetch/{id}")
    public ResponseEntity fetchById(@PathVariable Long id) {
        return response(Result.of(service.findById(id)));
    }


    @GetMapping(value = "/reservation/fetch/mine")
    public ResponseEntity fetchMine() {
        return response(Result.of(repo.findAllByCreatedByUserId(SecurityHolder.getProfileId())));
    }

    @PostMapping(value = "/reservation/create")
    public ResponseEntity create(@RequestBody Reservation value) {
        return response(service.create(value));
    }

    @PostMapping(value = "/reservation/move")
    public ResponseEntity moveTo(@RequestBody Reservation value) {
        return response(service.moveTo(value));
    }

    @PatchMapping(value = "/reservation/patch/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody List<ResourcePatch> patches) {
        return response(service.patch(id, patches));
    }

}
