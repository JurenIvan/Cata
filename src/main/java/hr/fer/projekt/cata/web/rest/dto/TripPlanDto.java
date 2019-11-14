package hr.fer.projekt.cata.web.rest.dto;

import hr.fer.projekt.cata.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
}
