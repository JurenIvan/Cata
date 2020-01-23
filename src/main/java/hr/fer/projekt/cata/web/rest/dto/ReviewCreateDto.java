package hr.fer.projekt.cata.web.rest.dto;

import hr.fer.projekt.cata.domain.Review;
import hr.fer.projekt.cata.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDto {

    private String content;
    private Integer grade;

    public Review toEntity(User user) {
        return user.addReview(new Review(null, content, grade, null));
    }
}
