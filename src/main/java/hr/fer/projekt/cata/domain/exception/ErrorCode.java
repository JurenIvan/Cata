package hr.fer.projekt.cata.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXCEPTION(40001, "Exception occurred", BAD_REQUEST),
    CONNECTION_FAILED(40002, "Connection failed", BAD_REQUEST),
    NO_SUCH_USER(40003, "No such user in database", BAD_REQUEST),
    NO_SUCH_TRIP(40004, "No such trip in database", BAD_REQUEST),
    NO_SUCH_TRIP_PLAN(40005, "No such trip plan in database", BAD_REQUEST),
    NOT_AN_ORGANIZER(40006, "Not an organizer", BAD_REQUEST),
    ALREADY_PASSENGER(40007, "User is already passenger", BAD_REQUEST),
    NOT_AN_PASSENGER(40008, "User is not a passenger of the trip", BAD_REQUEST),
    NO_SUCH_APIKEY(40009, "No such apikey", BAD_REQUEST),
    NO_SUCH_AUTH_METHOD(40010, "No such auth method implemented", BAD_REQUEST),
    VALIDATION_EXCEPTION(41000, "Validation constraint error", BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus status;
}
