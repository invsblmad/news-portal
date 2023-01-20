package kg.megalab.newsportal.dto.response.status;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Setter
@Getter
public class ValidationErrorResponse extends ErrorStatusResponse {
    private Map<String, String> errors;

    public ValidationErrorResponse(Map<String, String> errors) {
        super("Validation error", HttpStatus.BAD_REQUEST.value());
        this.errors = errors;
    }
}
