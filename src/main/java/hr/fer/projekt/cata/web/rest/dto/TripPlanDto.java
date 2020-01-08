package hr.fer.projekt.cata.web.rest.dto;

import hr.fer.projekt.cata.domain.Location;
import hr.fer.projekt.cata.domain.TripPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripPlanDto {

    private Long id;
    private String description;
    private List<LocationDto> locationList;
    private Integer minNumberOfPassengers;
    private String pictureUrl;

    public TripPlan toEntity(List<Location> locations) {
        return new TripPlan(null, description, locations, minNumberOfPassengers, pictureUrl);
    }
}
