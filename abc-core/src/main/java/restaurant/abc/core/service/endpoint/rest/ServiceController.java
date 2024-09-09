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

    @GetMapping(value = "/service/fetch/{id}")
    public ResponseEntity fetchById(@PathVariable Long id) {
        return response(Result.of(service.findById(id)));
    }

    @PostMapping(value = "/service/create")
    public ResponseEntity create(@RequestBody OfferedService value) {
        return response(service.create(value));
    }

    @PutMapping(value = "/service/update")
    public ResponseEntity update(@RequestBody OfferedService value) {
        return response(service.update(value));
    }

    @DeleteMapping(value = "/service/remove/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return response(Result.of(service.delete(id)));
    }
}
