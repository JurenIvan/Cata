package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.Location;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.domain.User;
import hr.fer.projekt.cata.domain.exception.CataException;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.LocationCreateDto;
import hr.fer.projekt.cata.web.rest.dto.TripCreateDto;
import hr.fer.projekt.cata.web.rest.dto.TripEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static hr.fer.projekt.cata.domain.enums.Role.ORGANIZER;
import static hr.fer.projekt.cata.domain.exception.ErrorCode.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripPlanRepository tripPlanRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final LocationRepository locationRepository;
    private final CamundaService camundaService;

    public List<Trip> getTrips() {
        return tripRepository.findAll();
    }

    public Trip editTrip(TripEditDto tripEditDto) {
        var loggedInUser = userDetailsService.getLoggedUser();

        if (!loggedInUser.getRoles().contains(ORGANIZER))
            throw new CataException(NOT_AN_ORGANIZER);

        Trip trip = tripRepository.findById(tripEditDto.getId()).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        trip.edit(tripEditDto);
        return tripRepository.save(trip);
    }

    public Trip createTrip(TripCreateDto tripCreateDto) {
        var loggedInUser = userDetailsService.getLoggedUser();

        if (!loggedInUser.getRoles().contains(ORGANIZER))
            throw new CataException(NOT_AN_ORGANIZER);

        TripPlan tripPlan;
        if (tripCreateDto.getTripPlanId() != null) {
            tripPlan = tripPlanRepository.findById(tripCreateDto.getTripPlanId()).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        } else {
            List<LocationCreateDto> locations = tripCreateDto.getTripPlanDto().getLocationList();
            List<Long> locationIds = tripCreateDto.getTripPlanDto().getLocationListIds();

            List<Location> locationList = null;
            if (locations != null) {
                locationList = locations.stream().map(e -> locationRepository.save(e.toLocation())).collect(toList());
            } else if (locationIds != null) {
                locationList = locationRepository.findByIdIn(locationIds);
            }

            tripPlan = tripPlanRepository.save(tripCreateDto.getTripPlanDto().toEntity(locationList));
        }
        Trip trip = new Trip(null, tripCreateDto.getStartDateTime(), tripCreateDto.getEndDateTime(), tripCreateDto.getPrice(), new ArrayList<>(), tripPlan);

        return tripRepository.save(trip);
    }

    public Trip joinTrip(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        User currUser = userDetailsService.getLoggedUser();
        if (trip.getPassengers().contains(currUser))
            throw new CataException(ALREADY_PASSENGER);

        trip.addPassenger(currUser);
        tripRepository.save(trip);
        camundaService.joinTrip(currUser.getId(), trip.getId());
        return trip;
    }

    public Trip cancelRegistration(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        User currUser = userDetailsService.getLoggedUser();
        if (!trip.getPassengers().contains(currUser))
            throw new CataException(NOT_AN_PASSENGER);

        trip.removePassenger(currUser);

        tripRepository.save(trip);
        camundaService.cancelReservation(currUser.getId(), trip.getId());
        return trip;
    }

    public Trip getTrip(Long tripId) {
        return tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
    }

    public List<Trip> getMyTrips() {
        return tripRepository.findAllByPassengersContaining(userDetailsService.getLoggedUser());
    }

    public List<Trip> cancelTrip(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        var user = userDetailsService.getLoggedUser();

        if (!user.getRoles().contains(ORGANIZER))
            throw new CataException(NOT_AN_ORGANIZER);

        camundaService.cancelTrip(tripId);
        tripRepository.delete(trip);
        return tripRepository.findAllByTripPlan(trip.getTripPlan());
    }

    public void payTrip(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        var user = userDetailsService.getLoggedUser();

        if (!trip.getPassengers().contains(user))
            throw new CataException(NOT_AN_PASSENGER);

        camundaService.userPaid(user.getId(), tripId);
    }
}
