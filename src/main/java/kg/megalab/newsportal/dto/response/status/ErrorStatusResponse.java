package kg.megalab.newsportal.dto.response.status;

public class ErrorStatusResponse extends StatusResponse {

    public ErrorStatusResponse(String message, int status) {
        super(message, status);
    }
}
