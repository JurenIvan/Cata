package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.web.rest.dto.TripDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private Integer passengerCount;

    @ManyToOne
    private TripPlan tripPlan;

    public TripDto toDto() {
        return new TripDto(id, startDateTime, endDateTime, price, passengerCount, tripPlan.toDto());
    }

    public void edit(TripDto tripDto) {
        if (tripDto.getEndDateTime() != null)
            this.endDateTime = tripDto.getEndDateTime();
        if (tripDto.getStartDateTime() != null)
            this.startDateTime = tripDto.getStartDateTime();
        if (tripDto.getPassengerCount() != null)
            this.passengerCount = tripDto.getPassengerCount();
    }
}
