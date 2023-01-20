package kg.megalab.newsportal.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentRequest {

    @NotNull(message = "The comment can't be null")
    @Size(max = 255, message = "The length of the comment exceeds character limit")
    private String text;
}
