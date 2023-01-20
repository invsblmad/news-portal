package kg.megalab.newsportal.repository.custom;

import kg.megalab.newsportal.dto.response.data.DetailedNewsDto;
import kg.megalab.newsportal.dto.response.data.NewsDto;
import kg.megalab.newsportal.dto.response.data.UserNewsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomNewsRepository {

    DetailedNewsDto findById(int newsId, Integer userId);
    Page<NewsDto> findAll(List<Integer> categories, String search, Integer userId, Pageable pageable);
    Page<NewsDto> findUserFavouriteNews(String search, int userId, Pageable pageable);
    Page<UserNewsDto> findAllUserNews(int userId, String search, Pageable pageable);
}
