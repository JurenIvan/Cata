package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.web.rest.dto.TripDto;
import hr.fer.projekt.cata.web.rest.dto.TripEditDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static hr.fer.projekt.cata.domain.enums.Role.ORGANIZER;
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

    public TripDto toDto(List<Role> roles) {
        if (roles.contains(ORGANIZER))
            return new TripDto(id, startDateTime, endDateTime, price, passengers.stream().map(User::toDto).collect(toList()), tripPlan.toDto());
        return new TripDto(id, startDateTime, endDateTime, price, null, tripPlan.toDto());

    }

    public void edit(TripEditDto tripEditDto) {
        if (tripEditDto.getEndDateTime() != null)
            this.endDateTime = tripEditDto.getEndDateTime();
        if (tripEditDto.getStartDateTime() != null)
            this.startDateTime = tripEditDto.getStartDateTime();
        if (tripEditDto.getPrice() != null)
            this.price = tripEditDto.getPrice();
    }

    public void removePassenger(User passenger) {
        passengers.remove(passenger);
    }

    public void addPassenger(User passenger) {
        this.passengers.add(passenger);
    }
}
