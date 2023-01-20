package kg.megalab.newsportal.dto.response.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewsDto {

    private int id;
    private String title;
    private String shortDescription;
    private String image;
    private LocalDate publishedDate;
    private boolean isLiked;

    @QueryProjection
    public NewsDto(int id, String title, String shortDescription, String image, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.image = image;
        this.publishedDate = publishedDate;
    }

    @QueryProjection
    public NewsDto(int id, String title, String shortDescription, String image, LocalDate publishedDate, boolean isLiked) {
        this(id, title, shortDescription, image, publishedDate);
        this.isLiked = isLiked;
    }
}
