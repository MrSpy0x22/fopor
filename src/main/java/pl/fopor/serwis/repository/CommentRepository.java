package pl.fopor.serwis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {

    /**
     * Pobieranie komentarzy dla postów przy użyciu paginacji.
     *
     * @author marcin22x
     * @param postId Identyfikator postu
     * @param pageable Ustawienia paginacji.
     * @return Lista obiektów typi {@code Page}.
     */
    @Query("SELECT c FROM Comment c WHERE c.commentPost.postId = :postId")
    Page<Comment> getCommentsForPost(@Param("postId") Integer postId, Pageable pageable);
}
