package hr.fer.projekt.cata.config.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationRequestDto {
    @NotBlank(message = "Username must be provided!")
    private String username;
    @NotBlank(message = "Password must be provided!")
    private String password;
}
