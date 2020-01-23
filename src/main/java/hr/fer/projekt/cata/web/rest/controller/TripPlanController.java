package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.service.TripPlanService;
import hr.fer.projekt.cata.web.rest.dto.ReviewCreateDto;
import hr.fer.projekt.cata.web.rest.dto.ReviewDto;
import hr.fer.projekt.cata.web.rest.dto.TripPlanDto;
import hr.fer.projekt.cata.web.rest.dto.TripPlanEditDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripPlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripPlanController.class);
    private final TripPlanService tripPlanService;

    @GetMapping
    public List<TripPlan> getTripPlans() {
        return tripPlanService.getTripPlans();
    }

    @GetMapping("/{tripPlanId}")
    public TripPlan getTripPlans(@PathVariable Long tripPlanId) {
        return tripPlanService.getTripPlan(tripPlanId);
    }

    @PostMapping("/create")
    public TripPlan createTripPlan(@RequestBody TripPlanDto tripPlanDto) {
        return tripPlanService.createTripPlan(tripPlanDto);
    }

    @PostMapping("/edit/{tripPlanId}")
    public TripPlan editTripPlan(@RequestBody TripPlanEditDto tripPlanEditDto, @PathVariable Long tripPlanId) {
        return tripPlanService.editTripPlan(tripPlanEditDto, tripPlanId);
    }

    @GetMapping("/reviews/{id}")
    private List<ReviewDto> getReviews(@PathVariable Long id) {
        return tripPlanService.getReviews(id);
    }

    @PostMapping("/create/review/{tripId}")
    private TripPlanDto createReview(@RequestBody ReviewCreateDto reviewCreateDto, @PathVariable Long tripId) {
        return tripPlanService.createReview(reviewCreateDto, tripId);
    }
}
