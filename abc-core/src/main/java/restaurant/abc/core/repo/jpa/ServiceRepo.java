package restaurant.abc.core.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.abc.core.domain.entity.OfferedService;

@Repository
public interface ServiceRepo extends JpaRepository<OfferedService, Long> {
}