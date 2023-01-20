package kg.megalab.newsportal.repository;

import kg.megalab.newsportal.model.Comment;
import kg.megalab.newsportal.repository.custom.CustomCommentsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer>, CustomCommentsRepository {
}
