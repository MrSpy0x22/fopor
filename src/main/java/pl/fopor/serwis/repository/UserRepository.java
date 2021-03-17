package pl.fopor.serwis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.fopor.serwis.model.User;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {
}
