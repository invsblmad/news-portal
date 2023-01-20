package kg.megalab.newsportal.repository;

import kg.megalab.newsportal.model.News;
import kg.megalab.newsportal.model.User;
import kg.megalab.newsportal.repository.custom.CustomNewsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface NewsRepository extends JpaRepository<News, Integer>, CustomNewsRepository {

    Optional<News> findByIdAndUser(int id, User user);
}
