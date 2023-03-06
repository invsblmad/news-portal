package kg.megalab.newsportal.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String image;
    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public News(String title, String shortDescription, String text, Category category, LocalDate date, User user) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.text = text;
        this.category = category;
        this.date = date;
        this.user = user;
    }
}
