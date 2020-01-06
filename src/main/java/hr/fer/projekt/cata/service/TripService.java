package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TripService {

    private TripRepository tripRepository;
    private TripPlanRepository tripPlanRepository;

    public List<TripDto> getTrips() {
        return tripRepository.findAll().stream().map(e -> e.toDto()).collect(toList());
    }

    public Trip editTrip(TripDto tripDto) {
        var trip = tripRepository.findById(tripDto.getId()).orElseThrow(() -> new CATAException());
        trip.edit(tripDto);
        tripPlanRepository.save(trip.getTripPlan());
        return tripRepository.save(trip);
    }

    public TripDto createTrip(TripDto tripDto) {
        var tripPlan = tripPlanRepository.findById(tripDto.getTripPlanDto().getId()).orElseThrow(() -> new CATAException());
        Trip trip = new Trip(null, tripDto.getStartDateTime(), tripDto.getEndDateTime(), tripDto.getPrice(), tripDto.getPassengerCount(), tripPlan);
        return tripRepository.save(trip).toDto();
    }
}
