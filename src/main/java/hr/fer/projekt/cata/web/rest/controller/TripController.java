package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.service.TripService;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/trips"))
@AllArgsConstructor
public class TripController {


    private TripService tripService;

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
}
