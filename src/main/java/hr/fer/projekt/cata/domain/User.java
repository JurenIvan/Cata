package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String email;
    private String username;
    private String passwordHash;
    private int yearOfBirth;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;
}
