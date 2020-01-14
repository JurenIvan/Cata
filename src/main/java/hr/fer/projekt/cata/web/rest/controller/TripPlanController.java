package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.service.TripPlanService;
import hr.fer.projekt.cata.web.rest.dto.ReviewCreateDto;
import hr.fer.projekt.cata.web.rest.dto.ReviewDto;
import hr.fer.projekt.cata.web.rest.dto.TripPlanDto;
import hr.fer.projekt.cata.web.rest.dto.TripPlanEditDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
public class TripPlanController {

    private TripPlanService tripPlanService;

    @CrossOrigin
    @GetMapping
    public List<TripPlan> getTripPlans() {
        return tripPlanService.getTripPlans();
    }

    @GetMapping("/{tripPlanId}")
    @CrossOrigin
    public TripPlan getTripPlans(@PathVariable Long tripPlanId) {
        return tripPlanService.getTripPlan(tripPlanId);
    }

    @PostMapping("/create")
    @CrossOrigin
    public TripPlan createTripPlan(@RequestBody TripPlanDto tripPlanDto) {
        return tripPlanService.createTripPlan(tripPlanDto);
    }

    @PostMapping("/edit")
    @CrossOrigin
    public TripPlan editTripPlan(@RequestBody TripPlanEditDto tripPlanEditDto) {
        return tripPlanService.editTripPlan(tripPlanEditDto);
    }

    @GetMapping("/reviews/{id}")
    @CrossOrigin
    private List<ReviewDto> getReviews(@PathVariable Long id) {
        return tripPlanService.getReviews(id);
    }

    @PostMapping("/create/review/{tripPlanId}")
    @CrossOrigin
    private TripPlanDto createReview(@RequestBody ReviewCreateDto reviewCreateDto, @PathVariable Long tripPlanId) {
        return tripPlanService.createReview(reviewCreateDto, tripPlanId);
    }
}
