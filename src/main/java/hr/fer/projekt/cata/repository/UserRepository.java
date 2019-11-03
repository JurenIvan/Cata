package hr.fer.projekt.cata.repository.UserRepository;

import hr.fer.projekt.cata.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findAllByUsername(String username);
    Optional<User> findAllByEmail(String username);

}
