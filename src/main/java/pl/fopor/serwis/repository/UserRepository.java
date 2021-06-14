package pl.fopor.serwis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {
    User findByUserMail(String name);
    List<User> findUserByUserFollowedPosts(Post post);
}
