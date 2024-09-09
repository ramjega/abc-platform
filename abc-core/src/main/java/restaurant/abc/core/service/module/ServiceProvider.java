
package restaurant.abc.core.service.module;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.service.common.Result;
import restaurant.abc.core.service.common.StatusCode;
import restaurant.abc.core.service.common.TxStatusCodes;

import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceProvider extends BaseResourceService<OfferedService> {

    public ServiceProvider(ApplicationContext ctx, JpaRepository<OfferedService, Long> repo) {
        super(ctx, OfferedService.class, repo);
    }

    @Override
    public Result<OfferedService> update(OfferedService value) {

        if (value.getId() == null) {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_BAD_REQUEST, "Field id cannot be null"));
        }

        Optional<OfferedService> existing = repo.findById(value.getId());

        if (existing.isPresent()) {
            if (value.getName() != null) {
                existing.get().setName(value.getName());
            }

            if (value.getDescription() != null) {
                existing.get().setDescription(value.getDescription());
            }

            if (value.getPrice() != null) {
                existing.get().setPrice(value.getPrice());
            }

            if (value.getServiceType() != null) {
                existing.get().setServiceType(value.getServiceType());
            }

            if (value.getAdditionalService() != null) {
                existing.get().setAdditionalService(value.getAdditionalService());
            }
            return Result.of(repo.save(existing.get()));
        } else {
            return Result.of(StatusCode.sc(TxStatusCodes.SC_NOT_FOUND, type().getSimpleName() + " for id [" + value.getId() + "] not found"));
        }
    }
}
