package restaurant.abc.core.service.endpoint.rest;


import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.abc.core.domain.entity.Query;
import restaurant.abc.core.repo.jpa.QueryRepo;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.module.QueryService;

import static restaurant.abc.core.service.common.Converter.response;


@RestController
@CrossOrigin
public class QueryController {
    private final QueryService service;
    private final QueryRepo repo;

    public QueryController(ApplicationContext context) {
        this.service = context.getBean(QueryService.class);
        this.repo = context.getBean(QueryRepo.class);
    }

    @GetMapping(value = "/query/fetch")
    public ResponseEntity fetch() {
        return response(Result.of(service.query()));
    }

    @GetMapping(value = "/query/fetch/{id}")
    public ResponseEntity fetchById(@PathVariable Long id) {
        return response(Result.of(repo.findAllByReservationId(id)));
    }

    @PostMapping(value = "/query/submit")
    public ResponseEntity create(@RequestBody Query value) {
        return response(service.create(value));
    }

    @PostMapping(value = "/query/reply/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Query value) {
        return response(service.reply(id, value));
    }
}
