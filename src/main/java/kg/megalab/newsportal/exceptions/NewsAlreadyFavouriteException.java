package kg.megalab.newsportal.exceptions;

public class NewsAlreadyFavouriteException extends RuntimeException {
    public NewsAlreadyFavouriteException(String message) {
        super(message);
    }
}
