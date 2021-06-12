package pl.fopor.serwis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Comment;
import pl.fopor.serwis.model.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {
    List<Comment> findByCommentPost(Post commentPost);
}
