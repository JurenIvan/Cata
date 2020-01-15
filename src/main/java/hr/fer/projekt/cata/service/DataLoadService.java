package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.security.util.JwtUtil;
import hr.fer.projekt.cata.domain.*;
import hr.fer.projekt.cata.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hr.fer.projekt.cata.domain.enums.Role.*;
import static java.util.List.of;


@Service
@AllArgsConstructor
public class DataLoadService implements ApplicationRunner {

	private UserRepository userRepository;
	private TripRepository tripRepository;
	private TripPlanRepository tripPlanRepository;
	private LocationRepository locationRepository;
	private ApikeyRepository apikeyRepository;
	private JwtUtil jwtTokenUtil;

	private CammundaService cammundaService;

	@Override
	public void run(ApplicationArguments args) {
		var locations = getLocationLists();
		getUsers();
		var tripPlanLists = getTripPlansList(locations);
		getTripList(tripPlanLists);
		setApikeys();

		startInstances();
	}

	private void startInstances() {

		cammundaService.joinTrip(10L,10L);

	}

	private void setApikeys() {
		List<ApiKey> apiKeys = apikeyRepository.saveAll(of(
				new ApiKey(1L, "CAMUNDA", BCrypt.hashpw("CAMUNDA_APIKEY", BCrypt.gensalt(12)), APIKEY_CAMUNDA))
		);

		System.out.println();
		apiKeys.forEach(e -> {
			String a = jwtTokenUtil.createApiKey(e.getApikeyUserName());
			System.out.printf("APIKEY %s->%s%n", e.getApikeyUserName(), a);
		});
		System.out.println();
	}

	private void getTripList(List<TripPlan> tripPlans) {
		Trip trip1 = new Trip((long) 1, LocalDateTime.of(2019, 12, 20, 10, 20), LocalDateTime.of(2019, 12, 27, 19, 0), (double) 7000, new ArrayList<>(), tripPlans.get(0));
		Trip trip2 = new Trip((long) 2, LocalDateTime.of(2020, 1, 3, 8, 30), LocalDateTime.of(2020, 1, 5, 20, 30), (double) 3000, new ArrayList<>(), tripPlans.get(1));
		Trip trip3 = new Trip((long) 3, LocalDateTime.of(2019, 12, 10, 13, 30), LocalDateTime.of(2019, 12, 20, 18, 30), (double) 10000, new ArrayList<>(), tripPlans.get(2));
		Trip trip4 = new Trip((long) 4, LocalDateTime.of(2020, 2, 1, 8, 30), LocalDateTime.of(2019, 2, 9, 19, 30), (double) 6700, new ArrayList<>(), tripPlans.get(3));

		tripRepository.saveAll(of(trip1, trip2, trip3, trip4));
	}

	private List<TripPlan> getTripPlansList(List<List<Location>> locations) {
		String[] descriptions = {"8 dana London i Pariz", "3 dana skijanja u Sloveniji",
				"10 dana na Baliju i Indoneziji", "6 dana u sjevernoj Italiji"};

		int[] minPassengers = {50, 35, 20, 45};
		String[] pictureUrls = {"https://imgix.bustle.com/uploads/shutterstock/2019/9/19/a49124d9-5f62-47a5-b5ec-8dd3a3066b30-shutterstock-1420728554.jpg?w=970&h=546&fit=crop&crop=faces&auto=format&q=70",
				"https://www.snowmagazine.com/media/reviews/photos/original/cc/f3/f1/five-reasons-to-ski-slovenia-74-1478102849.jpg",
				"https://i-a6eb.kxcdn.com/tours/bali-237196_202011.jpg.axd?width=665&crop=auto&scale=both&quality=100",
				"https://s27135.pcdn.co/wp-content/uploads/2018/11/How-to-see-the-very-best-of-Milan-in-one-day-878x585.jpg.optimal.jpg"};

		List<TripPlan> tripPlans = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			TripPlan tripPlan = new TripPlan(null, descriptions[i], locations.get(i), minPassengers[i], pictureUrls[i], new ArrayList<>());
			tripPlans.add(tripPlanRepository.save(tripPlan));
		}
		return tripPlans;
	}

	private List<List<Location>> getLocationLists() {
		List<List<Location>> listLocations = new ArrayList<>();
		List<Location> locations = new ArrayList<>();
		Location l1 = new Location(null, "Pariz", "Francuska");
		Location l2 = new Location(null, "London", "Ujedinjeno Kraljevstvo");
		locations.add(l1);
		locations.add(l2);
		listLocations.add(locationRepository.saveAll(locations));

		Location l3 = new Location(null, "Rogla", "Slovenija");
		locations = new ArrayList<>();
		locations.add(l3);
		listLocations.add(locationRepository.saveAll(locations));

		l3 = new Location(null, "Ubud", "Indonezija");
		Location l4 = new Location(null, "Nusa Penida", "Indonezija");
		locations = new ArrayList<>();
		locations.add(l3);
		locations.add(l4);
		listLocations.add(locationRepository.saveAll(locations));

		l3 = new Location(null, "Milano", "Italija");
		l4 = new Location(null, "Verona", "Italija");
		locations = new ArrayList<>();
		locations.add(l3);
		locations.add(l4);
		listLocations.add(locationRepository.saveAll(locations));

		return listLocations;
	}


	private List<User> getUsers() {
		List<User> users = new ArrayList<>();

		users.add(new User(1L, "admin@cata.com", "admin", BCrypt.hashpw("admin", BCrypt.gensalt(12)), 1950L, of(VISITOR, ORGANIZER)));
		users.add(new User(2L, "ppetric@cata.com", "ppetric", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1960L, of(VISITOR)));
		users.add(new User(3L, "ivan.juren@gmail.com", "iivanovic", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1998L, of(VISITOR)));
		users.add(new User(4L, "john.doe@cata.com", "john.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)));
		users.add(new User(5L, "mary.doe@cata.com", "mary.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)));
		users.add(new User(6L, "peter.doe@cata.com", "peter.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)));
		users.add(new User(7L, "mike.doe@cata.com", "mike.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)));

		return userRepository.saveAll(users);
	}
}
