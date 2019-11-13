package hr.fer.projekt.cata.rest.controller;

import hr.fer.projekt.cata.config.security.model.RegisterRequestDto;
import hr.fer.projekt.cata.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class RegisterController {

    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws Exception {
        userService.saveUser(registerRequestDto);
        return ResponseEntity.ok("Registration ok!");
    }
}
