package kg.megalab.newsportal.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne
    @JoinColumn(name = "replied_to")
    private Comment parentComment;

    public Comment(String text, LocalDate date, User user, News news, Comment parentComment) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.news = news;
        this.parentComment = parentComment;
    }
}
