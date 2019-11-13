package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.*;

@RestController("/trips")
@AllArgsConstructor
public class TripController {

    private TripRepository tripRepository;

    @GetMapping
    private List<TripDto> getTrips(){
        return tripRepository.findAll().stream().map(e->e.toDto()).collect(toList());
    }

}
