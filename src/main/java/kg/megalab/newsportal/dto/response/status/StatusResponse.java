package kg.megalab.newsportal.dto.response.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class StatusResponse {
    String message;
    int status;
}
