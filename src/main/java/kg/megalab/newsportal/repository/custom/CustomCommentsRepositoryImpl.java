package kg.megalab.newsportal.repository.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kg.megalab.newsportal.dto.response.data.CommentDto;
import kg.megalab.newsportal.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCommentsRepositoryImpl implements CustomCommentsRepository{

    private final EntityManager em;

    @Override
    public List<Comment> findAllByNewsId(int newsId) {
        Query query = em.createNativeQuery(
                "WITH RECURSIVE comments_tree AS (" +
                "SELECT id, text, date, user_id, replied_to, 0 AS generation_number " +
                "FROM comments WHERE comments.replied_to IS NULL and news_id = ?1 " +
                "UNION ALL " +
                "SELECT child.id, child.text, child.date, child.user_id, child.replied_to, " +
                "generation_number+1 AS generation_number " +
                "FROM comments child " +
                "JOIN comments_tree t ON t.id = child.replied_to) " +
                "SELECT comments_tree.id, text, date, users.username, replied_to, generation_number " +
                "FROM comments_tree JOIN users ON user_id = users.id", Tuple.class);
        query.setParameter(1, newsId);
//
//        List<Tuple> results = query.getResultList();
//
//        for (Tuple t : results) {
//            List<TupleElement<?>> cols = t.getElements();
//            for (TupleElement col : cols) {
//                System.out.println(t.get(col.getAlias()));
//            }
//        }
        return null;
    }
}
