package kg.megalab.newsportal.exceptions;

public class UserNotAuthorException extends RuntimeException {
    public UserNotAuthorException(String message) {
        super(message);
    }
}
