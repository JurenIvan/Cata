package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.config.security.model.RegisterRequestDto;
import hr.fer.projekt.cata.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class RegisterController {

    private UserService userService;

    @CrossOrigin
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws Exception {
        userService.saveUser(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
