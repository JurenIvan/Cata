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
public class Occasion {

    @Id
    private long id;

    private String title;
    private String description;
    @ManyToMany
    @JoinTable(name = "occasion_user",
            joinColumns = {@JoinColumn(name = "fk_occasion")},
            inverseJoinColumns = {@JoinColumn(name = "fk_user")})
    private List<User> participants;
}
