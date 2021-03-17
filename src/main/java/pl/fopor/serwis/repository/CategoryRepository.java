package pl.fopor.serwis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {
}
