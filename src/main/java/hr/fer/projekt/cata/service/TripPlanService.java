package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.web.rest.dto.TripPlanDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TripPlanService {

    private TripPlanRepository tripPlanRepository;
    private LocationRepository locationRepository;
    private UserDetailsServiceImpl userDetailsService;

    public List<TripPlan> getTripPlans() {
        return tripPlanRepository.findAll();
    }

    public TripPlan getTripPlan(long id) {
        return tripPlanRepository.findById(id).orElseThrow(() -> new CATAException());
    }

    public TripPlan createTripPlan(TripPlanDto tripPlanDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        var locations = tripPlanDto.getLocationList().stream().map(e -> e.toLocation()).collect(toList());
        locations = locationRepository.saveAll(locations);
        TripPlan tripPlan = new TripPlan(tripPlanDto, locations);
        return tripPlanRepository.save(tripPlan);
    }

    public TripPlan editTripPlan(TripPlanDto tripPlanDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        var tripPlan = tripPlanRepository.findById(tripPlanDto.getId()).orElseThrow(() -> new CATAException());
        var locations = tripPlanDto.getLocationList().stream().map(e -> e.toLocation()).collect(toList());
        locations = locationRepository.saveAll(locations);
        tripPlan.edit(tripPlanDto, locations);

        return tripPlanRepository.save(tripPlan);
    }
}
