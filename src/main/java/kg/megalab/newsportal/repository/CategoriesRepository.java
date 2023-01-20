package kg.megalab.newsportal.repository;

import kg.megalab.newsportal.dto.response.data.CategoryView;
import kg.megalab.newsportal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    List<CategoryView> findBy();
}
