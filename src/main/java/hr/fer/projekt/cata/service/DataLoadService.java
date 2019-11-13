package hr.fer.projekt.cata.service;

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

//        users.forEach(user -> userRepository.save(user));
//        tripList.forEach(user -> tripRepository.save(user));
//        tripPlansList.forEach(user -> tripPlanRepository.save(user));
    }

    private List<Trip> getTripList() {
        return null;
    }

    private List<TripPlan> getTripPlansList() {
        return null;
    }


    private List<User> getUsers() {
        return null;
    }
}
