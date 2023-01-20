package kg.megalab.newsportal.repository;

import kg.megalab.newsportal.model.FavouriteNews;
import kg.megalab.newsportal.model.FavouriteNewsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteNewsRepository extends JpaRepository<FavouriteNews, FavouriteNewsId> {

    Optional<FavouriteNews> findByUserIdAndNewsId(Integer userId, Integer newsId);
    void deleteByNewsId(Integer id);
}
