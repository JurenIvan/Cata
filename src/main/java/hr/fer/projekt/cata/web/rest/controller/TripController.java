package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.service.TripService;
import hr.fer.projekt.cata.web.rest.dto.TripCreateDto;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import hr.fer.projekt.cata.web.rest.dto.TripEditDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(("/trips"))
@RequiredArgsConstructor
public class TripController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final UserDetailsServiceImpl userDetailsService;
    private final TripService tripService;

    @GetMapping
    public List<TripDto> getTrips() {
        LOGGER.warn("getTrips");
        return convertToTripDtoList(tripService.getTrips());
    }

    @PostMapping("/create")
    public TripDto createTrip(@RequestBody TripCreateDto tripCreateDto) {
        LOGGER.warn("createTrip:" + tripCreateDto);
        return convertToTripDtoList(tripService.createTrip(tripCreateDto));
    }

    @PostMapping("/edit")
    public TripDto editTrip(@RequestBody TripEditDto tripEditDto) {
        LOGGER.warn("editTrip:" + tripEditDto);
        return convertToTripDtoList(tripService.editTrip(tripEditDto));
    }

    @GetMapping("/join")
    public TripDto joinTrip(@RequestParam Long id) {
        LOGGER.warn("joinTrip:" + id);
        return convertToTripDtoList(tripService.joinTrip(id));
    }

    @GetMapping("/cancel-registration")
    public TripDto cancelRegistration(@RequestParam Long id) {
        LOGGER.warn("cancelRegistration:" + id);
        return convertToTripDtoList(tripService.cancelRegistration(id));
    }

    @GetMapping("/{id}")
    public TripDto getTrip(@PathVariable Long id) {
        LOGGER.warn("getTrip:" + id);
        return convertToTripDtoList(tripService.getTrip(id));
    }

    @GetMapping("/my")
    public List<TripDto> myTrips() {
        LOGGER.warn("myTrips");
        return convertToTripDtoList(tripService.getMyTrips());
    }

    @GetMapping("/cancel/{tripId}")
    public List<TripDto> cancelTrip(@PathVariable Long tripId) {
        LOGGER.warn("cancelTrip:" + tripId);
        return convertToTripDtoList(tripService.cancelTrip(tripId));
    }

    @GetMapping("/pay/{tripId}")
    public void payTrip(@PathVariable Long tripId) {
        LOGGER.warn("payTrip:" + tripId);
        tripService.payTrip(tripId);
    }

    private List<TripDto> convertToTripDtoList(List<Trip> trips) {
        return trips.stream().map(e -> e.toDto(userDetailsService.getLoggedUser().getRoles())).collect(toList());
    }

    private TripDto convertToTripDtoList(Trip trips) {
        return trips.toDto(userDetailsService.getLoggedUser().getRoles());
    }
}
