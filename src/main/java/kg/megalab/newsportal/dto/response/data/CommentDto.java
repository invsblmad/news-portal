package kg.megalab.newsportal.dto.response.data;

import java.time.LocalDate;
import java.util.List;

public class CommentDto {
    private int id;
    private String text;
    private LocalDate date;
    private String username;
    private List<CommentDto> childComments;
}
