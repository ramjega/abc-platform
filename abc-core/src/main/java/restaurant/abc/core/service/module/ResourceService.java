package restaurant.abc.core.service.module;

import restaurant.abc.core.domain.entity.ResourceEntity;
import restaurant.abc.core.service.common.ResourcePatch;
import restaurant.abc.core.service.common.Result;

import java.util.List;
import java.util.Optional;

public interface ResourceService<T extends ResourceEntity> {

    Class<T> type();

    Result<T> create(T value);

    List<T> query();

    Optional<T> findById(Long id);

    Result<T> update(T value);

    Result<T> patch(Long id, List<ResourcePatch> patches);

    Result<T> delete(Long id);
}
