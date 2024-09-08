package restaurant.abc.core.service.endpoint.rest;


import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.abc.core.domain.entity.MenuItem;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.service.common.ResourcePatch;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.MenuItemService;
import restaurant.abc.core.service.module.ServiceProvider;

import java.util.List;

import static restaurant.abc.core.service.common.Converter.response;


@RestController
@CrossOrigin
public class MenuItemController {
    private final MenuItemService service;

    public MenuItemController(ApplicationContext context) {
        this.service = context.getBean(MenuItemService.class);
    }

    @GetMapping(value = "/menu-item/fetch")
    public ResponseEntity fetch() {
        return response(Result.of(service.query()));
    }

    @GetMapping(value = "/menu-item/fetch/{id}")
    public ResponseEntity fetchById(@PathVariable Long id) {
        return response(Result.of(service.findById(id)));
    }

    @PostMapping(value = "/menu-item/create")
    public ResponseEntity create(@RequestBody MenuItem value) {
        return response(service.create(value));
    }

    @DeleteMapping(value = "/menu-item/remove/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return response(service.delete(id));
    }

    @PatchMapping(value = "/menu-item/patch/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody List<ResourcePatch> patches) {
        return response(service.patch(id, patches));
    }

}
