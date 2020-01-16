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
import static java.lang.String.format;
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

	public void joinTrip(Long userID, Long TripId) {
		HttpResponse<String> a = null;
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

			a = client.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException | InterruptedException ignored) {
			System.out.println(ignored);
		}
		System.out.println(a);
	}

	public void cancelReservation(Long userId, Long tripID) {
		HttpResponse<String> a = null;
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/message"))
					.POST(ofString("{ \n" +
							"\t\"messageName\" : \"CancelTrip\",\n" +
							"  \"correlationKeys\" : { \n" +
							"  \t\"userId\" : {\"value\" : "+userId+", \"type\": \"Long\"}, \n" +
							"  \t\"tripId\" : {\"value\" : "+tripID+", \"type\": \"Long\"} \n" +
							"  }\n" +
							"}"))
					.header("Content-Type", "application/json")
					.build();

			a = client.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException | InterruptedException ignored) {
			System.out.println(ignored);
		}
		System.out.println(a);
	}

	public void reviewWritten(Long userId, Long tripId) {
		HttpResponse<String> a = null;
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/message"))
					.POST(ofString("{\n" +
							"  \"messageName\" : \"ReviewMessage\",\n" +
							"  \"correlationKeys\" : {\n" +
							"    \"userId\" : {\"value\" : "+userId+", \"type\": \"Long\"},\n" +
							"    \"tripId\" : {\"value\" : "+tripId+", \"type\": \"Long\"}\n" +
							"  }\n" +
							"}"))
					.header("Content-Type", "application/json")
					.build();


			a = client.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException | InterruptedException ignored) {
			System.out.println(ignored);
		}
		System.out.println(a);
	}

	public void userPaid(Long userId, Long tripId) {
		HttpResponse<String> a = null;
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/message"))
					.POST(ofString("{\n" +
							"  \"messageName\" : \"PaymentMessage\",\n" +
							"  \"correlationKeys\" : {\n" +
							"    \"userId\" : {\"value\" : "+userId+", \"type\": \"Long\"},\n" +
							"    \"tripId\" : {\"value\" : "+tripId+", \"type\": \"Long\"}\n" +
							"  }\n" +
							"}"))
					.header("Content-Type", "application/json")
					.build();


			a = client.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException | InterruptedException ignored) {
			System.out.println(ignored);
		}
		System.out.println(a);
	}

	public void cancelTrip(Long tripId) {
		HttpResponse<String> a = null;
		try {
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = newBuilder()
					.uri(create(BASE_URL + "/engine-rest/message"))
					.POST(ofString("{ \n" +
							"  \"messageName\" : \"TripCanceled\",\n" +
							"  \"correlationKeys\" : { \n" +
							"  \t\"tripId\" : {\"value\" : "+tripId+", \"type\": \"Long\"} \n" +
							"  }\n" +
							"}"))
					.header("Content-Type", "application/json")
					.build();

			a = client.send(request, HttpResponse.BodyHandlers.ofString());

		} catch (IOException | InterruptedException ignored) {
			System.out.println(ignored);
		}
		System.out.println(a);
	}

	public void remindToPay(Long userId, Long tripId) {
		System.out.println("remind user to pay. userid:" + userId + " tripid:" + tripId);
		var user = userRepository.findById(userId).orElseThrow(CATAException::new);
		var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);

		emailSender.sendMessage(user.getEmail(), "Reminder", "Please pay for trip " + trip.getTripPlan().getDescription());
	}

	public void notifyOrganizers(Long userId, Long tripId, String reason) {
		System.out.println("User canceled, reason" + reason + "tripID:" + tripId + "userId" + userId);
		var organizers = userRepository.findAllByRolesContaining(ORGANIZER);
		var user = userRepository.findById(userId).orElseThrow(CATAException::new);
		var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);

		organizers.forEach(e ->
				emailSender.sendMessage(
						e.getEmail(),
						"Cancelation",
						format("User %s has left trip %s due to %s.", user.getUsername(), trip.getTripPlan().getDescription(), reason))
		);
	}

	public void notifyPassengers(Long tripId) {
		System.out.println("Trip Canceled");
		var trip = tripRepository.findById(tripId).orElseThrow(CATAException::new);
		trip.getPassengers().forEach(e ->
				emailSender.sendMessage(e.getEmail(),
						"Trip cancelled",
						format("The trip %s has been canceled. Sorry for the inconvenience", trip.getTripPlan().getDescription())));
	}
}
