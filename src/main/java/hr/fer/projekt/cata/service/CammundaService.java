package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.domain.exception.CataException;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static hr.fer.projekt.cata.domain.enums.Role.ORGANIZER;
import static hr.fer.projekt.cata.domain.exception.ErrorCode.*;
import static java.lang.String.format;
import static java.net.URI.create;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpRequest.newBuilder;

@Service
@Data
@RequiredArgsConstructor
public class CammundaService {


    @Value("${camunda.deployment.url}")
    public static String BASE_URL;      //todo check

    public static final String CANCEL_TRIP = "CancelTrip";
    public static final String REVIEW_MESSAGE = "ReviewMessage";
    public static final String PAYMENT_MESSAGE = "PaymentMessage";
    public static final String TRIP_CANCELED = "TripCanceled";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    public static final String ENGINE_REST_MESSAGE = "/engine-rest/message";
    public static final String ENGINE_REST_PROCESS_DEFINITION_KEY_START_TRIP_START = "/engine-rest/process-definition/key/StartTrip/start";

    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final TripRepository tripRepository;
    private final TripPlanRepository tripPlanRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final LocationRepository locationRepository;

    public void joinTrip(Long userID, Long tripId) {
        try {
            HttpRequest request = newBuilder()
                    .uri(create(BASE_URL + ENGINE_REST_PROCESS_DEFINITION_KEY_START_TRIP_START))
                    .POST(ofString(createJson(userID, tripId)))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CataException(CONNECTION_FAILED, e);
        }
    }

    public void cancelReservation(Long userId, Long tripID) {
        try {
            HttpRequest request = newBuilder()
                    .uri(create(BASE_URL + ENGINE_REST_MESSAGE))
                    .POST(ofString(createJson(userId, tripID, CANCEL_TRIP)))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CataException(CONNECTION_FAILED, e);
        }
    }

    public void reviewWritten(Long userId, Long tripId) {
        try {
            HttpRequest request = newBuilder()
                    .uri(create(BASE_URL + ENGINE_REST_MESSAGE))
                    .POST(ofString(createJson(userId, tripId, REVIEW_MESSAGE)))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CataException(CONNECTION_FAILED, e);
        }
    }

    public void userPaid(Long userId, Long tripId) {
        try {
            HttpRequest request = newBuilder()
                    .uri(create(BASE_URL + ENGINE_REST_MESSAGE))
                    .POST(ofString(createJson(userId, tripId, PAYMENT_MESSAGE)))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CataException(CONNECTION_FAILED, e);
        }
    }

    public void cancelTrip(Long tripId) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = newBuilder()
                    .uri(create(BASE_URL + ENGINE_REST_MESSAGE))
                    .POST(ofString(createJson(tripId)))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CataException(CONNECTION_FAILED, e);
        }
    }

    public void remindToPay(Long userId, Long tripId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new CataException(NO_SUCH_USER));
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));

        emailSender.sendMessage(user.getEmail(), "Reminder", "Please pay for trip " + trip.getTripPlan().getDescription());
    }

    public void notifyOrganizers(Long userId, Long tripId, String reason) {
        var organizers = userRepository.findAllByRolesContaining(ORGANIZER);
        var user = userRepository.findById(userId).orElseThrow(() -> new CataException(NO_SUCH_USER));
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));

        organizers.forEach(e -> emailSender.sendMessage(
                e.getEmail(),
                "Cancelation",
                format("User %s has left trip %s due to %s.", user.getUsername(), trip.getTripPlan().getDescription(), reason))
        );
    }

    public void notifyPassengers(Long tripId) {
        var trip = tripRepository.findById(tripId).orElseThrow(() -> new CataException(NO_SUCH_TRIP));
        trip.getPassengers().forEach(e ->
                emailSender.sendMessage(e.getEmail(),
                        "Trip cancelled",
                        format("The trip %s has been canceled. Sorry for the inconvenience", trip.getTripPlan().getDescription())));
    }


    private String createJson(Long tripId) {
        return "{ \n" +
                "  \"messageName\" : \"" + TRIP_CANCELED + "\",\n" +
                "  \"correlationKeys\" : { \n" +
                "  \t\"tripId\" : {\"value\" : " + tripId + ", \"type\": \"Long\"} \n" +
                "  }\n" +
                "}";
    }

    private String createJson(Long userID, Long tripId) {
        return "{\n" +
                "  \"variables\": {\n" +
                "    \"userId\": {\"value\":" + userID + ",\"type\":\"long\"},\n" +
                "    \"tripId\": {\"value\":" + tripId + ",\"type\":\"long\"}\n" +
                "  }\n" +
                "}";
    }

    private String createJson(Long userId, Long tripId, String messageName) {
        return "{\n" +
                "  \"messageName\" : \"" + messageName + "\",\n" +
                "  \"correlationKeys\" : {\n" +
                "    \"userId\" : {\"value\" : " + userId + ", \"type\": \"Long\"},\n" +
                "    \"tripId\" : {\"value\" : " + tripId + ", \"type\": \"Long\"}\n" +
                "  }\n" +
                "}";
    }
}
