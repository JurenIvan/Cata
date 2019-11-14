package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.web.rest.dto.TripPlanDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TripPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ElementCollection
    private List<Location> locationList;
    private Integer minNumberOfPassengers;
    @Column(length = 4095)
    private String pictureUrl;

    public TripPlan(TripPlanDto tripPlanDto, List<Location> locations) {
        this.description = tripPlanDto.getDescription();
        this.locationList = locations;
        this.minNumberOfPassengers = tripPlanDto.getMinNumberOfPassengers();
        this.pictureUrl = tripPlanDto.getPictureUrl();
    }

    public TripPlanDto toDto() {
        return new TripPlanDto(id, description, locationList.stream().map(e -> e.toDto()).collect(toList()), minNumberOfPassengers, pictureUrl);
    }

    public void edit(TripPlanDto tripPlanDto, List<Location> locations) {
        if (tripPlanDto.getDescription() != null)
            this.description = tripPlanDto.getDescription();
        if (locations != null && !locations.isEmpty())
            this.locationList = locations;
        if (tripPlanDto.getMinNumberOfPassengers() != null)
            this.minNumberOfPassengers = tripPlanDto.getMinNumberOfPassengers();
        if (tripPlanDto.getPictureUrl() != null)
            this.pictureUrl = tripPlanDto.getPictureUrl();
    }
}
