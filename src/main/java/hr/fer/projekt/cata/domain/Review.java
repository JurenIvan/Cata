package hr.fer.projekt.cata.domain;

import hr.fer.projekt.cata.web.rest.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Integer grade;

    @ManyToOne
    private User author;

    public ReviewDto toDto() {
        return new ReviewDto(id, content, grade, author.getId());
    }

}
