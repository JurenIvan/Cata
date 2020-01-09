package hr.fer.projekt.cata.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCreateDto {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double price;

    private Long tripPlanId;
    private TripPlanCreateDto tripPlanDto;
}
