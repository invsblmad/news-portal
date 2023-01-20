package kg.megalab.newsportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "favourite_news")
@Data
@NoArgsConstructor
@IdClass(FavouriteNewsId.class)
public class FavouriteNews implements Serializable {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "news_id")
    private Integer newsId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "news_id", insertable = false, updatable = false)
    private News news;

    public FavouriteNews(Integer userId, Integer newsId) {
        this.userId = userId;
        this.newsId = newsId;
    }
}
