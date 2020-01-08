package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.Location;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.domain.User;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.TripCreateDto;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import hr.fer.projekt.cata.web.rest.dto.TripEditDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TripService {

    private TripRepository tripRepository;
    private TripPlanRepository tripPlanRepository;
    private UserDetailsServiceImpl userDetailsService;
    private LocationRepository locationRepository;


    public List<TripDto> getTrips() {
        return tripRepository.findAll().stream().map(Trip::toDto).collect(toList());
    }

    public TripDto editTrip(TripEditDto tripEditDto) {
        var loggedInUser = userDetailsService.getLoggedUser();

        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        Trip trip = tripRepository.findById(tripEditDto.getId()).orElseThrow(CATAException::new);
        trip.edit(tripEditDto);
        return tripRepository.save(trip).toDto();
    }

    public TripDto createTrip(TripCreateDto tripCreateDto) {
        var loggedInUser = userDetailsService.getLoggedUser();

        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        TripPlan tripPlan;
        if (tripCreateDto.getTripPlanid() != null) {
            tripPlan = tripPlanRepository.findById(tripCreateDto.getId()).orElseThrow(CATAException::new);
        } else {
            var locations=tripCreateDto.getTripPlanDto().getLocationList().stream().map(e->locationRepository.save(e.toLocation())).collect(toList());
            tripPlan = tripPlanRepository.save(tripCreateDto.getTripPlanDto().toEntity(locations));
        }
        Trip trip = new Trip(null, tripCreateDto.getStartDateTime(), tripCreateDto.getEndDateTime(), tripCreateDto.getPrice(), new ArrayList<>(), tripPlan);

        return tripRepository.save(trip).toDto();
    }

    public Trip joinTrip(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);
        hr.fer.projekt.cata.domain.User currUser = userDetailsService.getLoggedUser();
        trip.addPassenger(currUser);
        tripRepository.save(trip);
        return trip;
    }

    public TripDto cancelRegistration(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);
        User currUser = userDetailsService.getLoggedUser();
        trip.removePassenger(currUser);
        tripRepository.save(trip);
        return trip.toDto();
    }

    public TripDto getTrip(Long tripId) {
        return tripRepository.findById(tripId).orElseThrow(CATAException::new).toDto();
    }
}
