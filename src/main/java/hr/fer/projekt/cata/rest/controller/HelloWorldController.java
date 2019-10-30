package hr.fer.projekt.cata.rest.controller;

import hr.fer.projekt.cata.domain.Occasion;
import hr.fer.projekt.cata.service.OccasionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class HelloWorldController {

    private OccasionService occasionService;

    public HelloWorldController(OccasionService occasionService){
        this.occasionService=occasionService;
    }

    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hello!";
    }

    @RequestMapping("/get-occasion")
    public List<Occasion> getOccasions() {
        return occasionService.findAllOccasions();
    }

}
