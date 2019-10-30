package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.domain.Occasion;
import hr.fer.projekt.cata.repository.OccasionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OccasionService {

    private OccasionRepository occasionRepository;

    public List<Occasion> findAllOccasions() {
        return occasionRepository.findAll();
    }

    public Occasion findAllOccasions(long id) {
        return occasionRepository.findById(id).orElseThrow(CATAException::new);
    }
}
