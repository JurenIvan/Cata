package hr.fer.projekt.cata.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class CataValidationException extends RuntimeException {

	private BindingResult bindingResult;
}
