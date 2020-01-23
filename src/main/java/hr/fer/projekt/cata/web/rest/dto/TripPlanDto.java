package hr.fer.projekt.cata.web.rest.dto;

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
    private List<ReviewDto> reviewDtos;
    private List<Long> tripIds;
}
