package hr.fer.projekt.cata.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class CataValidationException extends RuntimeException {

	private BindingResult bindingResult;
}
