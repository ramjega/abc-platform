
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.MenuItem;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.common.StatusCode;
import restaurant.abc.core.service.common.TxStatusCodes;

import java.util.Optional;

@org.springframework.stereotype.Service
public class MenuItemService extends BaseResourceService<MenuItem> {

    public MenuItemService(ApplicationContext ctx, JpaRepository<MenuItem, Long> repo) {
        super(ctx, MenuItem.class, repo);
    }

    @Override
    public Result<MenuItem> update(MenuItem value) {

        if (value.getId() == null) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_BAD_REQUEST, "Field id cannot be null"));
        }

        Optional<MenuItem> existing = repo.findById(value.getId());

        if (existing.isPresent()) {
            existing.get().setName(value.getName());
            existing.get().setDescription(value.getDescription());
            existing.get().setPrice(value.getPrice());
            existing.get().setType(value.getType());
            existing.get().setVegetarian(value.isVegetarian());

            return Result.of(repo.save(existing.get()));
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, type().getSimpleName() + " for id [" + value.getId() + "] not found"));
        }
    }
}
