package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double price;

    @ManyToMany
    private List<User> passengers;

    @ManyToOne
    private TripPlan tripPlan;

    public TripDto toDto() {
        return new TripDto(id, startDateTime, endDateTime, price, passengers.stream().map(User::toDto).collect(toList()), tripPlan.toDto());
    }

    public void edit(TripDto tripDto) {
        if (tripDto.getEndDateTime() != null)
            this.endDateTime = tripDto.getEndDateTime();
        if (tripDto.getStartDateTime() != null)
            this.startDateTime = tripDto.getStartDateTime();
    }

    public void addPassenger(User passenger) {
        this.passengers.add(passenger);
    }

    public void removePassenger(User passenger) {
        this.passengers.remove(passenger);
    }
}
