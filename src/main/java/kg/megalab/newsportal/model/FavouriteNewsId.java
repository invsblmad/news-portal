package kg.megalab.newsportal.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavouriteNewsId implements Serializable {
    private Integer userId;
    private Integer newsId;
}
