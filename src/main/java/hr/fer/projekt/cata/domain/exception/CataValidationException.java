package hr.fer.projekt.cata.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class GigerValidationException extends RuntimeException {

	private BindingResult bindingResult;
}
