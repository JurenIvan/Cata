package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.Review;
import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.ReviewRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.*;
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
    private ReviewRepository reviewRepository;
    private CammundaService cammundaService;

    public List<TripPlan> getTripPlans() {
        return tripPlanRepository.findAll();
    }

    public TripPlan getTripPlan(long id) {
        return tripPlanRepository.findById(id).orElseThrow(CATAException::new);
    }

    public TripPlan createTripPlan(TripPlanDto tripPlanDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        var locations = tripPlanDto.getLocationList().stream().map(LocationDto::toLocation).collect(toList());
        locations = locationRepository.saveAll(locations);
        TripPlan tripPlan = new TripPlan(tripPlanDto, locations);
        return tripPlanRepository.save(tripPlan);
    }

    public TripPlan editTripPlan(TripPlanDto tripPlanDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(Role.ORGANIZER))
            throw new CATAException();

        var tripPlan = tripPlanRepository.findById(tripPlanDto.getId()).orElseThrow(CATAException::new);
        var locations = tripPlanDto.getLocationList().stream().map(LocationDto::toLocation).collect(toList());
        locations = locationRepository.saveAll(locations);
        tripPlan.edit(tripPlanDto, locations);

        return tripPlanRepository.save(tripPlan);
    }

    public List<ReviewDto> getReviews(Long tripId) {
        return tripPlanRepository.findById(tripId).orElseThrow(CATAException::new).getReviews().stream().map(Review::toDto).collect(toList());
    }

    public TripPlanDto createReview(ReviewCreateDto reviewCreateDto, Long tripPlanId) {
    	var loggedInUser=userDetailsService.getLoggedUser();
        TripPlan tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(CATAException::new);
        Review review = reviewRepository.save(reviewCreateDto.toEntity(loggedInUser));

        tripPlan.addReview(review);
        cammundaService.reviewWritten(loggedInUser.getId(),tripPlanId);
        return tripPlanRepository.save(tripPlan).toDto();
    }
}
