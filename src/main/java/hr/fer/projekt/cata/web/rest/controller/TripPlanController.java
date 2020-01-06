package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.service.TripPlanService;
import hr.fer.projekt.cata.web.rest.dto.TripPlanDto;
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
    public TripPlan editTripPlan(@RequestBody TripPlanDto tripPlanDto) {
        return tripPlanService.editTripPlan(tripPlanDto);
    }
}
