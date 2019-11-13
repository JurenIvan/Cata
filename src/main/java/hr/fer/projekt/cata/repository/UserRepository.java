package hr.fer.projekt.cata.repository;

import hr.fer.projekt.cata.domain.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findAllByUsername(String username);
    Optional<Organizer> findAllByEmail(String username);

}
