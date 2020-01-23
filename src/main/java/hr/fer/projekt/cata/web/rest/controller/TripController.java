package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.service.TripService;
import hr.fer.projekt.cata.web.rest.dto.TripCreateDto;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import hr.fer.projekt.cata.web.rest.dto.TripEditDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/trips"))
@RequiredArgsConstructor
public class TripController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);
    private final TripService tripService;

    @GetMapping
    private List<TripDto> getTrips() {
        LOGGER.warn("getTrips");
        return tripService.getTrips();
    }

    @PostMapping("/create")
    private TripDto createTrip(@RequestBody TripCreateDto tripCreateDto) {
        LOGGER.warn("createTrip:" + tripCreateDto);
        return tripService.createTrip(tripCreateDto);
    }

    @PostMapping("/edit")
    private TripDto editTrip(@RequestBody TripEditDto tripEditDto) {
        LOGGER.warn("editTrip:" + tripEditDto);
        return tripService.editTrip(tripEditDto);
    }

    @GetMapping("/join")
    private TripDto joinTrip(@RequestParam Long id) {
        LOGGER.warn("joinTrip:" + id);
        return tripService.joinTrip(id);
    }

    @GetMapping("/cancel-registration")
    private TripDto cancelRegistration(@RequestParam Long id) {
        LOGGER.warn("cancelRegistration:" + id);
        return tripService.cancelRegistration(id);
    }

    @GetMapping("/{id}")
    private TripDto getTrip(@PathVariable Long id) {
        LOGGER.warn("getTrip:" + id);
        return tripService.getTrip(id);
    }

    @GetMapping("/my")
    private List<TripDto> myTrips() {
        LOGGER.warn("myTrips");
        return tripService.getMyTrips();
    }

    @GetMapping("/cancel/{tripId}")
    private List<TripDto> cancelTrip(@PathVariable Long tripId) {
        LOGGER.warn("cancelTrip:" + tripId);
        return tripService.cancelTrip(tripId);
    }

    @GetMapping("/pay/{tripId}")
    private void payTrip(@PathVariable Long tripId) {
        LOGGER.warn("payTrip:" + tripId);
        tripService.payTrip(tripId);
    }
}
