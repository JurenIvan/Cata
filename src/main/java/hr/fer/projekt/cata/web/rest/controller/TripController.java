package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.service.TripService;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/trips"))
@AllArgsConstructor
public class TripController {

    private TripService tripService;

    @CrossOrigin
    @GetMapping
    private List<TripDto> getTrips() {
        return tripService.getTrips();
    }

    @PostMapping("/create")
    private TripDto createTrip(TripDto tripDto) {
        return tripService.createTrip(tripDto);
    }

    @PostMapping("/edit")
    private TripDto editTrip(TripDto tripDto) {
        return tripService.editTrip(tripDto);
    }

    @GetMapping("/join/{id}")
    private TripDto joinTrip(@PathVariable Long tripId) {
        return tripService.joinTrip(tripId);
    }
}
