package hr.fer.projekt.cata.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripPlanEditDto {

    private String description;
    private List<Long> locationListIds;
    private Integer minNumberOfPassengers;
    private String pictureUrl;
}
