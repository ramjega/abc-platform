package restaurant.abc.core.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.abc.core.domain.entity.MenuItem;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
}