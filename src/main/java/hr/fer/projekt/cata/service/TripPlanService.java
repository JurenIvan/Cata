package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.Review;
import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.domain.exception.CataException;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.ReviewRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.web.rest.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static hr.fer.projekt.cata.domain.enums.Role.ORGANIZER;
import static hr.fer.projekt.cata.domain.exception.ErrorCode.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TripPlanService {

    private final TripPlanRepository tripPlanRepository;
    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final ReviewRepository reviewRepository;
    private final CammundaService cammundaService;

    public List<TripPlan> getTripPlans() {
        return tripPlanRepository.findAll();
    }

    public TripPlan getTripPlan(long id) {
        return tripPlanRepository.findById(id).orElseThrow(() -> new CataException(NO_SUCH_TRIP_PLAN));
    }

    public TripPlan createTripPlan(TripPlanDto tripPlanDto) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(ORGANIZER))
            throw new CataException(NOT_AN_ORGANIZER);

        var locations = tripPlanDto.getLocationList().stream().map(LocationDto::toLocation).collect(toList());
        locations = locationRepository.saveAll(locations);
        TripPlan tripPlan = new TripPlan(tripPlanDto, locations);
        return tripPlanRepository.save(tripPlan);
    }

    public TripPlan editTripPlan(TripPlanEditDto tripPlanDto, Long tripPlanId) {
        var loggedInUser = userDetailsService.getLoggedUser();
        if (!loggedInUser.getRoles().contains(ORGANIZER))
            throw new CataException(NOT_AN_ORGANIZER);

        var tripPlan = tripPlanRepository.findById(tripPlanId).orElseThrow(()->new CataException(NO_SUCH_TRIP));
        var locations = locationRepository.findByIdIn(tripPlanDto.getLocationListIds());

        tripPlan.edit(tripPlanDto, locations);

        return tripPlanRepository.save(tripPlan);
    }

    public List<ReviewDto> getReviews(Long tripId) {
        return tripPlanRepository.findById(tripId).orElseThrow(()->new CataException(NO_SUCH_TRIP_PLAN)).getReviews().stream().map(Review::toDto).collect(toList());
    }

    public TripPlanDto createReview(ReviewCreateDto reviewCreateDto, Long tripId) {
        var loggedInUser = userDetailsService.getLoggedUser();
        var trip = tripRepository.findById(tripId).orElseThrow(()->new CataException(NO_SUCH_TRIP));

        Review review = reviewRepository.save(reviewCreateDto.toEntity(loggedInUser));
        TripPlan tripPlan = trip.getTripPlan();
        tripPlan.addReview(review);

        cammundaService.reviewWritten(loggedInUser.getId(), tripId);
        return tripPlanRepository.save(tripPlan).toDto();
    }
}
