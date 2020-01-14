package hr.fer.projekt.cata.repository;

import hr.fer.projekt.cata.domain.ApiKey;
import hr.fer.projekt.cata.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApikeyRepository extends JpaRepository<ApiKey, Long> {

	Optional<ApiKey> findByApikeyUserName(String apikeyUserName);
}
