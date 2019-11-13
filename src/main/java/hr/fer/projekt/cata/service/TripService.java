package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TripService {

    private TripRepository tripRepository;

    public List<TripDto> getTrips() {
        return tripRepository.findAll().stream().map(e -> e.toDto()).collect(toList());
    }
}
