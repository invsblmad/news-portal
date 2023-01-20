package kg.megalab.newsportal.exceptions.handler;

import kg.megalab.newsportal.dto.response.status.ErrorStatusResponse;
import kg.megalab.newsportal.dto.response.status.ValidationErrorResponse;
import kg.megalab.newsportal.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class CustomExceptionHandler {

    private static final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    private static final HttpStatus forbidden = HttpStatus.FORBIDDEN;
    private static final HttpStatus notFound = HttpStatus.NOT_FOUND;

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<ErrorStatusResponse> handle(UsernameNotUniqueException e) {
        return ResponseEntity
                .status(badRequest)
                .body(new ErrorStatusResponse(e.getMessage(), badRequest.value()));
    }

    @ExceptionHandler(PasswordNotConfirmedException.class)
    public ResponseEntity<ErrorStatusResponse> handle(PasswordNotConfirmedException e) {
        return ResponseEntity
                .status(badRequest)
                .body(new ErrorStatusResponse(e.getMessage(), badRequest.value()));
    }

    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<ErrorStatusResponse> handle(NewsNotFoundException e) {
        return ResponseEntity
                .status(notFound)
                .body(new ErrorStatusResponse(e.getMessage(), notFound.value()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorStatusResponse> handle(CategoryNotFoundException e) {
        return ResponseEntity
                .status(badRequest)
                .body(new ErrorStatusResponse(e.getMessage(), badRequest.value()));
    }

    @ExceptionHandler(NewsAlreadyFavouriteException.class)
    public ResponseEntity<ErrorStatusResponse> handle(NewsAlreadyFavouriteException e) {
        return ResponseEntity
                .status(badRequest)
                .body(new ErrorStatusResponse(e.getMessage(), badRequest.value()));
    }

    @ExceptionHandler(UserNotAuthorException.class)
    public ResponseEntity<ErrorStatusResponse> handle(UserNotAuthorException e) {
        return ResponseEntity
                .status(forbidden)
                .body(new ErrorStatusResponse(e.getMessage(), forbidden.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handle(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(badRequest).body(new ValidationErrorResponse(errors));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorStatusResponse> handle(FileStorageException e) {
        return ResponseEntity
                .status(badRequest)
                .body(new ErrorStatusResponse(e.getMessage(), badRequest.value()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorStatusResponse> handle(CommentNotFoundException e) {
        return ResponseEntity
                .status(notFound)
                .body(new ErrorStatusResponse(e.getMessage(), notFound.value()));
    }
}
