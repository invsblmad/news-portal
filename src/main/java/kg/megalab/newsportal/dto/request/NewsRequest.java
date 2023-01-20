package kg.megalab.newsportal.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class NewsRequest {

    @NotBlank(message = "The title can't be null or empty")
    @Size(max = 255, message = "The length of title exceeds character limit")
    private String title;

    @NotBlank(message = "The description can't be null or empty")
    @Size(max = 255, message = "The length of description exceeds character limit")
    private String shortDescription;

    @NotBlank(message = "The text can't be null or empty")
    private String text;

    @NotNull(message = "The id of a category can't be null")
    @Positive(message = "The id of a category must be positive")
    private Integer categoryId;
}
