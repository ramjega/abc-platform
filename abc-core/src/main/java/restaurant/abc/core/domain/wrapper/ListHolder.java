package restaurant.abc.core.domain.wrapper;

import lombok.Data;
import restaurant.abc.core.domain.entity.ResourceEntity;

import java.util.List;

@Data
public class ListHolder<T extends ResourceEntity> {
    public ListHolder(List<T> data) {
        this.data = data;
    }

    private List<T> data;
}
