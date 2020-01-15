package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.errorHandling.CATAException;
import hr.fer.projekt.cata.config.security.UserDetailsServiceImpl;
import hr.fer.projekt.cata.repository.LocationRepository;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static hr.fer.projekt.cata.domain.enums.Role.ORGANIZER;
import static java.net.URI.create;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpRequest.newBuilder;

@Service
@Data
@AllArgsConstructor
public class CammundaService {

	//	@Value("${camunda.deployment.url}")
	public static final String BASE_URL = "http://localhost:8082";


	private UserRepository userRepository;
	private EmailSender emailSender;
	private TripRepository tripRepository;
	private TripPlanRepository tripPlanRepository;
	private UserDetailsServiceImpl userDetailsService;
	private LocationRepository locationRepository;
	private CammundaService cammundaService;

	public void joinTrip(Long userID, Long TripId) {
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/process-definition/key/StartTrip/start"))
					.POST(ofString("{\n" +
							"  \"variables\": {\n" +
							"    \"userId\": {\"value\":" + userID + ",\"type\":\"long\"},\n" +
							"    \"tripId\": {\"value\":" + TripId + ",\"type\":\"long\"}\n" +
							"  }\n" +
							"}"))
					.header("Content-Type", "application/json")
					.build();

			client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException ignored) {
		}
	}

	public void cancelReservation(Long userId, Long tripID) {
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/process-definition/key/StartTrip/start"))
					.POST(ofString("{\n" +
							"  \"messageName\" : \"PaymentMessage\",\n" +
							"  \"correlationKeys\" : {\n" +
							"    \"userId\" : {\"value\" : " + userId + ", \"type\": \"Long\"}\n" +
							"    \"tripId\" : {\"value\" : " + tripID + ", \"type\": \"Long\"}\n" +
							"  }}"))
					.header("Content-Type", "application/json")
					.build();

			client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException ignored) {
		}
	}

	public void reviewWritten(Long userId, Long tripPlanId) {
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/process-definition/key/StartTrip/start"))
					.POST(ofString("{\n" +
							"  \"messageName\" : \"ReviewMessage\",\n" +
							"  \"correlationKeys\" : {\n" +
							"    \"userId\" : {\"value\" : " + userId + ", \"type\": \"Long\"}\n" +
							"    \"tripPlanId\" : {\"value\" : " + tripPlanId + ", \"type\": \"Long\"}\n" +
							"  }}"))
					.header("Content-Type", "application/json")
					.build();

			client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException ignored) {
		}
	}

	public void cancelTrip(Long tripId) {
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/process-definition/key/StartTrip/start"))
					.POST(ofString("{\n" +
							"  \"messageName\" : \"ReviewMessage\",\n" +
							"  \"correlationKeys\" : {\n" +
							"    \"tripID\" : {\"value\" : " + tripId + ", \"type\": \"Long\"}\n" +
							"  }}"))
					.header("Content-Type", "application/json")
					.build();

			client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException ignored) {
		}
	}

	public void remindToPay(Long userId, Long tripId) {
		var user = userRepository.findById(userId).orElseThrow(CATAException::new);
		var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);

		emailSender.sendMessage(user.getEmail(), "Reminder", "Please pay for trip " + trip.getTripPlan().getDescription());
	}

	public void notifyOrganizers(Long userId, Long tripId) {
		var organizers = userRepository.findAllByRolesContaining(ORGANIZER);
		var user = userRepository.findById(userId).orElseThrow(CATAException::new);
		var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);

		organizers.forEach(e -> emailSender.sendMessage(e.getEmail(), "User left trip", "User " + user.getUsername() + " has left trip " + trip.getTripPlan().getDescription() + "."));
	}

	public void notifyPassengers(Long tripId) {
		var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);

		trip.getPassengers().forEach(e -> emailSender.sendMessage(e.getEmail(), "Trip cancelled", String.format("The trip %s has been canceled. Sorry for the inconvenience", trip.getTripPlan().getDescription())));
	}
}
