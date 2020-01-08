package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.service.TripService;
import hr.fer.projekt.cata.web.rest.dto.TripCreateDto;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import hr.fer.projekt.cata.web.rest.dto.TripEditDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/trips"))
@AllArgsConstructor
public class TripController {

    private TripService tripService;

    @GetMapping
    @CrossOrigin
    private List<TripDto> getTrips() {
        return tripService.getTrips();
    }

    @PostMapping("/create")
    @CrossOrigin
    private TripDto createTrip(@RequestBody TripCreateDto tripCreateDto) {
        return tripService.createTrip(tripCreateDto);
    }

    @PostMapping("/edit")
    @CrossOrigin
    private TripDto editTrip(@RequestBody TripEditDto tripEditDto) {
        return tripService.editTrip(tripEditDto);
    }

    @GetMapping("/join/{id}")
    @CrossOrigin
    private TripDto joinTrip(@PathVariable Long id) {
        return tripService.joinTrip(id);
    }

    @GetMapping("/cancel-registration/{id}")
    @CrossOrigin
    private TripDto cancelRegistration(@PathVariable Long id) {
        return tripService.cancelRegistration(id);
    }

    @GetMapping("/{id}")
    @CrossOrigin
    private TripDto getTrip(@PathVariable Long id) {
        return tripService.getTrip(id);
    }
}
