package hr.fer.projekt.cata.repository;


import hr.fer.projekt.cata.domain.TripPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripPlanRepository extends JpaRepository<TripPlan, Long> {
}
