package pl.fopor.serwis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post , Integer> {
    Page<Post> findByPostSolvedFalse(Pageable pageable);
    List<Post> findPostByPostFollowedBy(User user);
    Page<Post> findByPostTitleContaining(String title, Pageable pageable);
}
