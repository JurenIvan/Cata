package hr.fer.zemris.opp.giger.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import static hr.fer.zemris.opp.giger.domain.exception.ErrorCode.VALIDATION_EXCEPTION;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViolationError {

	private String path;
	private Integer code;
	private String message;

	public ViolationError(int code, String message) {
		this.path = "";
		this.code = code;
		this.message = message;
	}

	public ViolationError(FieldError fieldError) {
		this.path = fieldError.getField();
		this.code = VALIDATION_EXCEPTION.getCode();
		this.message = fieldError.getDefaultMessage();
	}
}
