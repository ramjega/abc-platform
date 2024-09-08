
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.MenuItem;

@org.springframework.stereotype.Service
public class MenuItemService extends BaseResourceService<MenuItem> {

    public MenuItemService(ApplicationContext ctx, JpaRepository<MenuItem, Long> repo) {
        super(ctx, MenuItem.class, repo);
    }
}
