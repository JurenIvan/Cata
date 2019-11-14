package hr.fer.projekt.cata.config.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponseDto {

    private String token;
}
