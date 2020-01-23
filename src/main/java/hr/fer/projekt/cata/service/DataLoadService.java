package hr.fer.projekt.cata.service;

import hr.fer.projekt.cata.config.security.util.JwtUtil;
import hr.fer.projekt.cata.domain.*;
import hr.fer.projekt.cata.repository.*;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DataLoadService implements ApplicationRunner {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final TripPlanRepository tripPlanRepository;
    private final LocationRepository locationRepository;
    private final ApikeyRepository apikeyRepository;
    private final JwtUtil jwtTokenUtil;

    private List<Location> locations = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<TripPlan> tripPlans = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();
    private List<ApiKey> apiKeys = new ArrayList<>();


    @Override
    public void run(ApplicationArguments args) {
        insertDefaultUsers();
        insertDefaultLocationLists();
        insertDefaultTripPlansList();
        insertDefaultTripList();
        insertDefaultApikeys();

        startInstances();
    }

    private void insertDefaultLocationLists() {
        locations = locationRepository.saveAll(of(
                new Location(null, "Pariz", "Francuska"),
                new Location(null, "London", "Ujedinjeno Kraljevstvo"),
                new Location(null, "Manchester", "Ujedinjeno Kraljevstvo"),
                new Location(null, "Rogla", "Slovenija"),
                new Location(null, "Ubud", "Indonezija"),
                new Location(null, "Nusa Penida", "Indonezija"),
                new Location(null, "Milano", "Italija"),
                new Location(null, "Verona", "Italija")));
    }

    private void insertDefaultApikeys() {
        apiKeys = apikeyRepository.saveAll(of(new ApiKey(1L, "CAMUNDA", BCrypt.hashpw("CAMUNDA_APIKEY", BCrypt.gensalt(12)), APIKEY_CAMUNDA)));
        OutputApiKeys(apiKeys);
    }

    private void OutputApiKeys(List<ApiKey> apiKeys) {
        apiKeys.forEach(e -> System.out.printf("\nAPIKEY %s->%s%n\n", e.getApikeyUserName(), jwtTokenUtil.createApiKey(e.getApikeyUserName())));
    }

    private void insertDefaultTripList() {
        trips = tripRepository.saveAll(of(
                new Trip((long) 1, LocalDateTime.of(2019, 12, 20, 10, 20), LocalDateTime.of(2019, 12, 27, 19, 0), (double) 7000, new ArrayList<>(), tripPlans.get(0)),
                new Trip((long) 2, LocalDateTime.of(2020, 1, 3, 8, 30), LocalDateTime.of(2020, 1, 5, 20, 30), (double) 3000, new ArrayList<>(), tripPlans.get(1)),
                new Trip((long) 3, LocalDateTime.of(2019, 12, 10, 13, 30), LocalDateTime.of(2019, 12, 20, 18, 30), (double) 10000, new ArrayList<>(), tripPlans.get(2)),
                new Trip((long) 4, LocalDateTime.of(2020, 2, 1, 8, 30), LocalDateTime.of(2019, 2, 9, 19, 30), (double) 6700, new ArrayList<>(), tripPlans.get(3)),
                new Trip((long) 5, LocalDateTime.of(2019, 12, 20, 10, 20), LocalDateTime.of(2009, 12, 27, 19, 0), (double) 8000, new ArrayList<>(), tripPlans.get(0))));
    }

    private void insertDefaultTripPlansList() {
        String[] pictureUrls = {
                "https://imgix.bustle.com/uploads/shutterstock/2019/9/19/a49124d9-5f62-47a5-b5ec-8dd3a3066b30-shutterstock-1420728554.jpg?w=970&h=546&fit=crop&crop=faces&auto=format&q=70",
                "https://www.snowmagazine.com/media/reviews/photos/original/cc/f3/f1/five-reasons-to-ski-slovenia-74-1478102849.jpg",
                "https://i-a6eb.kxcdn.com/tours/bali-237196_202011.jpg.axd?width=665&crop=auto&scale=both&quality=100",
                "https://s27135.pcdn.co/wp-content/uploads/2018/11/How-to-see-the-very-best-of-Milan-in-one-day-878x585.jpg.optimal.jpg",
                "https://www.visitmanchester.com/imageresizer/?image=%2Fdmsimgs%2Fchristmas-markets-at-albert-square_1__441639496.jpg&action=ProductDetailFullWidth2"};

        tripPlans = tripPlanRepository.saveAll(of(
                new TripPlan(null, "8 dana London i Pariz", of(locations.get(0), locations.get(1)), 10, pictureUrls[0], of()),
                new TripPlan(null, "3 dana skijanja u Sloveniji", of(locations.get(2), locations.get(3)), 15, pictureUrls[1], of()),
                new TripPlan(null, "10 dana na Baliju i Indoneziji", of(locations.get(4), locations.get(5)), 20, pictureUrls[2], of()),
                new TripPlan(null, "6 dana u sjevernoj Italiji", of(locations.get(6), locations.get(7)), 25, pictureUrls[3], of()),
                new TripPlan(null, "Najljesi vikend ikad", of(locations.get(8), locations.get(9)), 30, pictureUrls[4], of())));
    }

    private void insertDefaultUsers() {
        users = userRepository.saveAll(of(
                new User(1L, "admin@cata.com", "admin", BCrypt.hashpw("admin", BCrypt.gensalt(12)), 1950L, of(VISITOR, ORGANIZER)),
                new User(2L, "ppetric@cata.com", "ppetric", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1960L, of(VISITOR)),
                new User(3L, "ivan.juren@gmail.com", "iivanovic", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1998L, of(VISITOR)),
                new User(4L, "john.doe@cata.com", "john.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)),
                new User(5L, "mary.doe@cata.com", "mary.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)),
                new User(6L, "peter.doe@cata.com", "peter.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR)),
                new User(7L, "mike.doe@cata.com", "mike.doe", BCrypt.hashpw("12345678", BCrypt.gensalt(12)), 1990L, of(VISITOR))));
    }

    private void startInstances() {
        //	cammundaService.joinTrip(2L, 1L);
    }
}
