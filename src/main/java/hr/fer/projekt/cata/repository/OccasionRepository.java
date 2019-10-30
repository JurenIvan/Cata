package hr.fer.projekt.cata.repository;

import hr.fer.projekt.cata.domain.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, Long> {

    List<Occasion> findAll();

    Optional<Occasion> findById(long id);
}
