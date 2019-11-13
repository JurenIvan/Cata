package hr.fer.projekt.cata.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class HelloWorldController {

    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hello!";
    }

}
