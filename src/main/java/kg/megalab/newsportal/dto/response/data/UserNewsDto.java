package kg.megalab.newsportal.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserNewsDto {

    private int id;
    private String title;
    private String shortDescription;
    private String image;
    private LocalDate publishedDate;
}
