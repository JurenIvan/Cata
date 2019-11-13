package hr.fer.projekt.cata.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private int minNumberOfPassengers;
    @Column(length = 4095)
    private String pictureUrl;


}
