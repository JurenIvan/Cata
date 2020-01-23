package hr.fer.projekt.cata.web.rest.handler;

import hr.fer.projekt.cata.domain.exception.ApiError;
import hr.fer.projekt.cata.domain.exception.CataException;
import hr.fer.projekt.cata.domain.exception.CataValidationException;
import hr.fer.projekt.cata.domain.exception.ViolationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static hr.fer.projekt.cata.domain.exception.ErrorCode.EXCEPTION;
import static hr.fer.projekt.cata.domain.exception.ErrorCode.VALIDATION_EXCEPTION;
import static java.util.List.of;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CataException.class)
    protected ResponseEntity<Object> handleCataException(CataException ex) {
        return new ResponseEntity<>(
                new ApiError(EXCEPTION.getCode(), EXCEPTION.getMessage(), of(new ViolationError(ex.getErrorCode().getCode(), ex.getErrorCode().getMessage()))),
                new HttpHeaders(),
                ex.getErrorCode().getStatus());
    }

    @ExceptionHandler(CataValidationException.class)
    protected ResponseEntity<Object> handleCataValidationException(CataValidationException ex) {
        return new ResponseEntity<>(
                new ApiError(VALIDATION_EXCEPTION.getCode(), EXCEPTION.getMessage(), ex.getBindingResult().getAllErrors().stream().map(e -> new ViolationError((FieldError) e)).collect(toList())),
                new HttpHeaders(),
                BAD_REQUEST);
    }
}
