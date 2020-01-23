package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.domain.enums.Role;
import hr.fer.projekt.cata.web.rest.dto.UserDto;
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
    private Long id;
    private String email;
    private String username;
    private String passwordHash;
    private Long yearOfBirth;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "author")
    private List<Review> reviews;

    public UserDto toDto() {
        return new UserDto(id, username, email);
    }

    public Review addReview(Review review) {
        reviews.add(review);
        review.setAuthor(this);
        return review;
    }
}
