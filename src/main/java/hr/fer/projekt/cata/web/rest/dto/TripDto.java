package hr.fer.projekt.cata.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double price;
    private List<UserDto> passengers;
    private TripPlanDto tripPlanDto;
}
