package hr.fer.projekt.cata.web.rest.dto;

import hr.fer.projekt.cata.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private Double x;
    private Double y;

    private String name;
    private String country;

    public Location toLocation() {
        return new Location(null, x, y, name, country);
    }
}
