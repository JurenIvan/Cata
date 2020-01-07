package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.domain.Trip;
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
    @CrossOrigin
    private TripDto createTrip(@RequestBody TripDto tripDto) {
        return tripService.createTrip(tripDto);
    }

    @PostMapping("/edit")
    @CrossOrigin
    private Trip editTrip(@RequestBody TripDto tripDto) {
        return tripService.editTrip(tripDto);
    }

    @RequestMapping("/join")
    @CrossOrigin
    private Trip joinTrip(Long id) {
        return tripService.joinTrip(id);
    }
}
