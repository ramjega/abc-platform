
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.Query;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.common.StatusCode;
import restaurant.abc.core.service.common.TxStatusCodes;

import java.util.Optional;

@org.springframework.stereotype.Service
public class QueryService extends BaseResourceService<Query> {

    public QueryService(ApplicationContext ctx, JpaRepository<Query, Long> repo) {
        super(ctx, Query.class, repo);
    }

    public Result<Query> reply(Long id, Query value) {

        Optional<Query> existing = repo.findById(id);

        if (existing.isPresent()) {
            existing.get().setResponse(value.getResponse());
            return Result.of(repo.save(existing.get()));
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, type().getSimpleName() + " for id [" + id + "] not found"));
        }
    }
}
