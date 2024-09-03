package restaurant.abc.core.service.endpoint.rest;


import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.service.common.ResourcePatch;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.ServiceProvider;

import java.util.List;

import static restaurant.abc.core.service.common.Converter.response;


@RestController
@CrossOrigin
public class ServiceController {
    private final ServiceProvider service;

    public ServiceController(ApplicationContext context) {
        this.service = context.getBean(ServiceProvider.class);
    }

    @GetMapping(value = "/service/fetch")
    public ResponseEntity fetch() {
        return response(Result.of(service.query()));
    }

    @PostMapping(value = "/service/create")
    public ResponseEntity create(@RequestBody OfferedService value) {
        return response(service.create(value));
    }

    @PatchMapping(value = "/service/patch/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody List<ResourcePatch> patches) {
        return response(service.patch(id, patches));
    }

}
