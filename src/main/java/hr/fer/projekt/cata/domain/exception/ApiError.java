package hr.fer.zemris.opp.giger.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

	private Integer code;
	private String message;
	private List<ViolationError> violationErrors;
}
