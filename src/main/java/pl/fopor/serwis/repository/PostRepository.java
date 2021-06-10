package pl.fopor.serwis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post , Integer> {
    List<Post> findByPostSolvedFalse();
}
