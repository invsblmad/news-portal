package kg.megalab.newsportal.dto.response.status;

import org.springframework.http.HttpStatus;

public class SuccessStatusResponse extends StatusResponse {

    public SuccessStatusResponse() {
        super("Successful request", HttpStatus.OK.value());
    }
}
