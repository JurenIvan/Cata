package hr.fer.projekt.cata.config.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequestDto {

    @Email
    @NotBlank(message = "Email must be provided!")
    private String email;
    @NotBlank(message = "Username must be provided!")
    private String username;
    @Length(min = 8, message = "Password should be at least 8 chars long!")
    @NotBlank(message = "Password must be provided!")
    private String password;
}
