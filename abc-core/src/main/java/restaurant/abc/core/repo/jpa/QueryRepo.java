package restaurant.abc.core.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.abc.core.domain.entity.OfferedService;
import restaurant.abc.core.domain.entity.Query;

import java.util.List;

@Repository
public interface QueryRepo extends JpaRepository<Query, Long> {
    List<Query> findAllByReservationId(Long id);
}