package hr.fer.projekt.cata.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class CataException extends RuntimeException {

    private ErrorCode errorCode;
    private BindingResult bindingResult;

    public CataException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public CataException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CataException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}

