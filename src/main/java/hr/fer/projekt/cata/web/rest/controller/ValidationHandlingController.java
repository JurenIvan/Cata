package hr.fer.projekt.cata.web.rest.controller;

import hr.fer.projekt.cata.domain.exception.CataValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

public class ValidationHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandlingController.class);

    private void handleValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.warn("Validation Exception: " + bindingResult.getAllErrors());
            throw new CataValidationException(bindingResult);
        }
    }
}
