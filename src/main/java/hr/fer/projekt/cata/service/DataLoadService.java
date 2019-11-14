package hr.fer.projekt.cata.service;

import ch.qos.logback.core.CoreConstants;
import hr.fer.projekt.cata.domain.Location;
import hr.fer.projekt.cata.domain.Trip;
import hr.fer.projekt.cata.domain.TripPlan;
import hr.fer.projekt.cata.domain.User;
import hr.fer.projekt.cata.repository.TripPlanRepository;
import hr.fer.projekt.cata.repository.TripRepository;
import hr.fer.projekt.cata.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Service
@AllArgsConstructor
public class DataLoadService implements ApplicationRunner {

    private UserRepository userRepository;
    private TripRepository tripRepository;
    private TripPlanRepository tripPlanRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = getUsers();
        List<TripPlan> tripPlansList = getTripPlansList();
        List<Trip> tripList = getTripList();

        users.forEach(user -> userRepository.save(user));
        tripList.forEach(trip -> tripRepository.save(trip));
        tripPlansList.forEach(plan -> tripPlanRepository.save(plan));
    }

    private List<Trip> getTripList() {
        Trip trip1 = new Trip((long) 1, LocalDateTime.of(2019, 12, 20, 10, 20), LocalDateTime.of(2019, 12, 27, 19, 0), (double) 7000, 33, getTripPlansList().get(0));
        Trip trip2 =  new Trip((long) 2, LocalDateTime.of(2020, 1, 3, 8, 30), LocalDateTime.of(2020, 1, 5, 20, 30), (double) 3000, 14, getTripPlansList().get(1));
        Trip trip3 =  new Trip((long) 3, LocalDateTime.of(2019, 12, 10, 13, 30), LocalDateTime.of(2019, 12, 20, 18, 30), (double) 10000, 11, getTripPlansList().get(2));
        Trip trip4 =  new Trip((long) 4, LocalDateTime.of(2020, 2, 1, 8, 30), LocalDateTime.of(2019, 2, 9, 19, 30), (double) 6700, 11, getTripPlansList().get(0));

        return Arrays.asList(trip1, trip2, trip3, trip4);
    }

    private List<TripPlan> getTripPlansList() {
        String[] descriptions = {"8 dana London i Pariz", "3 dana skijanja u Sloveniji",
                  "10 dana na Baliju i Indoneziji", "6 dana u sjevernoj Italiji"};

        int[] minPassengers = {50, 35, 20, 45};
        String[] pictureUrls = { "https://imgix.bustle.com/uploads/shutterstock/2019/9/19/a49124d9-5f62-47a5-b5ec-8dd3a3066b30-shutterstock-1420728554.jpg?w=970&h=546&fit=crop&crop=faces&auto=format&q=70",
                "https://www.snowmagazine.com/media/reviews/photos/original/cc/f3/f1/five-reasons-to-ski-slovenia-74-1478102849.jpg"
                , "https://www.integral-zagreb.hr/sites/default/files/styles/1920_auto_/public/uploads/products/gallery/2019-09/bali-putovanje-6.jpg?itok=--216mi9",
                "https://s27135.pcdn.co/wp-content/uploads/2018/11/How-to-see-the-very-best-of-Milan-in-one-day-878x585.jpg.optimal.jpg"};

        List<List<Location>> locations = getLocationLists();

        List<TripPlan> tripPlans = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            TripPlan tripPlan = new TripPlan((long) (i+1), descriptions[i], locations.get(i), minPassengers[i], pictureUrls[i]);
            tripPlans.add(tripPlan);
        }

        return tripPlans;
    }

    private List<List<Location>> getLocationLists() {
        List<List<Location>> listLocations = new ArrayList<>();
        List<Location> locations = new ArrayList<>();
        Location l1 = new Location((long) 1, null, null, "Pariz", "Francuska");
        Location l2 = new Location((long) 2, null, null, "London", "Ujedinjeno Kraljevstvo");
        locations.add(l1);
        locations.add(l2);
        listLocations.add(locations);

        Location l3 = new Location((long) 3, null, null, "Rogla", "Slovenija");
        locations = new ArrayList<>();
        locations.add(l3);
        listLocations.add(locations);

        l3 = new Location((long) 4,null, null, "Ubud", "Indonezija");
        Location l4 = new Location((long) 5, null, null, "Nusa Penida", "Indonezija");
        locations = new ArrayList<>();
        locations.add(l3);
        locations.add(l4);
        listLocations.add(locations);

        l3 = new Location((long) 5, null, null, "Milano", "Italija");
        l4 = new Location((long) 6, null, null, "Verona", "Italija");
        locations = new ArrayList<>();
        locations.add(l3);
        locations.add(l4);
        listLocations.add(locations);

        return listLocations;
    }


    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String[] names = {"ppetric", "mmarkovic", "iivanovic", "ssaric"};
        String[] emails = {"petar.petric@email.com", "marko.markovic@email.com", "ivan.ivanovic@email.com", "sara.saric@email.com"};
        int[] years = {1987, 1962, 1996, 1983};
        String[] passwords = {"pass12pass", "password", "12345678", "banana"};

        for (int i = 0; i < 4; i++){
            User user = new User((long) (i+1), names[i], emails[i], passwords[i], years[i]);
            users.add(user);
        }
        return users;
    }

}
