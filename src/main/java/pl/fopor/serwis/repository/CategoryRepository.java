package pl.fopor.serwis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    @Query("SELECT c FROM Category c WHERE c.categoryPosts.size > 0")
    Page<Category> findNonEmpty(Pageable pageable);

    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE CONCAT('%' , :text , '%') ")
    Page<Category> findAllBySearchCriteria(@Param("text") String text , Pageable pageable);
}
