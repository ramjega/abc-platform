
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.OfferedService;

@org.springframework.stereotype.Service
public class ServiceProvider extends BaseResourceService<OfferedService> {

    public ServiceProvider(ApplicationContext ctx, JpaRepository<OfferedService, Long> repo) {
        super(ctx, OfferedService.class, repo);
    }
}
