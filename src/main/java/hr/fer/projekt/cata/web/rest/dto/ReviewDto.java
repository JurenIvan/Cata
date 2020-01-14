package hr.fer.projekt.cata.web.rest.dto;

import hr.fer.projekt.cata.domain.Review;
import hr.fer.projekt.cata.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    private String content;
    private Integer grade;
    private Long userId;

    public Review toEntity(User user) {
        return new Review(null, content, grade, user);
    }
}
