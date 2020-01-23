package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.config.security.model.RegisterRequestDto;
import hr.fer.projekt.cata.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) throws Exception {
        LOGGER.info("registerUser:" + registerRequestDto.toString());
        userService.saveUser(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
