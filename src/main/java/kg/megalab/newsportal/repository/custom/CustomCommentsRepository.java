package kg.megalab.newsportal.repository.custom;

import kg.megalab.newsportal.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCommentsRepository {

    List<Comment> findAllByNewsId(int newsId);
}
