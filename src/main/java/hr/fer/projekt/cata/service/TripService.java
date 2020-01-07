package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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


    public List<TripDto> getTrips() {
        return tripRepository.findAll().stream().map(Trip::toDto).collect(toList());
    }

    public Trip editTrip(TripDto tripDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        var trip = tripRepository.findById(tripDto.getId()).orElseThrow(CATAException::new);
        trip.edit(tripDto);
        tripPlanRepository.save(trip.getTripPlan());
        return tripRepository.save(trip);
    }

    public TripDto createTrip(TripDto tripDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        var tripPlan = tripPlanRepository.findById(tripDto.getTripPlanDto().getId()).orElseThrow(CATAException::new);
        Trip trip = new Trip(null, tripDto.getStartDateTime(), tripDto.getEndDateTime(), tripDto.getPrice(), tripDto.getPassengerCount(), new ArrayList<>(), tripPlan);
        return tripRepository.save(trip).toDto();
    }

    public Trip joinTrip(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);
        hr.fer.projekt.cata.domain.User currUser = userDetailsService.getLoggedUser();
        trip.addPassenger(currUser);
        tripRepository.save(trip);
        return trip;
    }
}
