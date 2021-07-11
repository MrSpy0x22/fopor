package pl.fopor.serwis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post , Integer> {
    Page<Post> findByPostSolvedFalse(Pageable pageable);
    List<Post> findPostByPostFollowedBy(User user);
    Page<Post> findByPostTitleContaining(String title, Pageable pageable);

    // Moje
    @Query("SELECT p FROM Post p WHERE p.postSolved = :solved")
    Page<Post> findPreviewForAll(@Param("solved") Boolean solved , Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postSolved = :solved AND p.postCategory.categoryId = :category")
    Page<Post> findPreviewForCategory(@Param("solved") Boolean solved , @Param("category") Integer category , Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postCategory.categoryId = :category")
    Page<Post> findPreviewForCategory(@Param("category") Integer category , Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postTitle LIKE CONCAT('%' , :text , '%') OR p.postContent LIKE CONCAT('%' , :text , '%')")
    Page<Post> findAllBySearchCriteria(@Param("text") String text , Pageable pageable);

    Page<Post> findAllByPostFollowedBy(@Param("user") User user , Pageable pageable);

    List<Post> findPostsByPostCreationTimeLessThan(LocalDateTime postCreationTime);

    Long countAllByPostSolved(boolean solved);
}
