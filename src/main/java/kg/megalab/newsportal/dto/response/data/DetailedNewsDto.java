package kg.megalab.newsportal.dto.response.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DetailedNewsDto {

    private int id;
    private String title;
    private String shortDescription;
    private String text;
    private String image;
    private LocalDate publishedDate;
    private boolean isLiked;

    @QueryProjection
    public DetailedNewsDto(int id, String title, String shortDescription, String text, String image, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.text = text;
        this.image = image;
        this.publishedDate = publishedDate;
    }

    @QueryProjection
    public DetailedNewsDto(int id, String title, String shortDescription, String text, String image, LocalDate publishedDate, boolean isLiked) {
        this(id, title, shortDescription, text, image, publishedDate);
        this.isLiked = isLiked;
    }

}
