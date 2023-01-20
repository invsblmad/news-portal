package kg.megalab.newsportal.service;

import kg.megalab.newsportal.dto.request.CommentRequest;
import kg.megalab.newsportal.dto.request.NewsRequest;
import kg.megalab.newsportal.dto.response.data.DetailedNewsDto;
import kg.megalab.newsportal.dto.response.data.NewsDto;
import kg.megalab.newsportal.dto.response.status.SuccessStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {

    Page<NewsDto> getALlNews(List<Integer> categories, String search, Pageable pageable);
    DetailedNewsDto getNewsById(int id);
    ResponseEntity<SuccessStatusResponse> deleteNewsById(int id);
    ResponseEntity<SuccessStatusResponse> createNews(NewsRequest newsRequest);
    ResponseEntity<SuccessStatusResponse> uploadNewsImage(int id, MultipartFile file);
    Page<NewsDto> getUserFavouriteNews(String search, Pageable pageable);
    ResponseEntity<SuccessStatusResponse> addNewsToFavourites(int id);
    ResponseEntity<SuccessStatusResponse> deleteNewsFromFavourites(int id);
    ResponseEntity<SuccessStatusResponse> writeComment(int newsId, CommentRequest commentRequest);
    ResponseEntity<SuccessStatusResponse> replyToComment(int newsId, int commentId,
                                                         CommentRequest commentRequest);
}
