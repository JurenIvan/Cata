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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor
public class TripPlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripPlanController.class);
    private final TripPlanService tripPlanService;

    @GetMapping
    public List<TripPlanDto> getTripPlans() {
        LOGGER.info("getTripPlans");
        return tripPlanService.getTripPlans().stream().map(TripPlan::toDto).collect(toList());
    }

    @GetMapping("/{tripPlanId}")
    public TripPlanDto getTripPlan(@PathVariable Long tripPlanId) {
        LOGGER.info("getTripPlan" + tripPlanId);
        return tripPlanService.getTripPlan(tripPlanId).toDto();
    }

    @PostMapping("/create")
    public TripPlanDto createTripPlan(@RequestBody TripPlanDto tripPlanDto) {
        LOGGER.info("createTripPlan");
        return tripPlanService.createTripPlan(tripPlanDto).toDto();
    }

    @PostMapping("/edit/{tripPlanId}")
    public TripPlanDto editTripPlan(@RequestBody TripPlanEditDto tripPlanEditDto, @PathVariable Long tripPlanId) {
        LOGGER.info("editTripPlan" + tripPlanEditDto + " tripPlanId" + tripPlanId);
        return tripPlanService.editTripPlan(tripPlanEditDto, tripPlanId).toDto();
    }

    @GetMapping("/reviews/{id}")
    public List<ReviewDto> getReviews(@PathVariable Long id) {
        LOGGER.info("getReviews" + id);
        return tripPlanService.getReviews(id);
    }

    @PostMapping("/create/review/{tripId}")
    public TripPlanDto createReview(@RequestBody ReviewCreateDto reviewCreateDto, @PathVariable Long tripId) {
        LOGGER.info("createReview" + reviewCreateDto + " tripId" + tripId);
        return tripPlanService.createReview(reviewCreateDto.getContent(), reviewCreateDto.getGrade(), tripId);
    }
}
