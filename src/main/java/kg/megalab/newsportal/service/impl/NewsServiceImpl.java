package kg.megalab.newsportal.service.impl;

import kg.megalab.newsportal.dto.request.CommentRequest;
import kg.megalab.newsportal.dto.request.NewsRequest;
import kg.megalab.newsportal.dto.response.status.SuccessStatusResponse;
import kg.megalab.newsportal.dto.response.data.DetailedNewsDto;
import kg.megalab.newsportal.dto.response.data.NewsDto;
import kg.megalab.newsportal.exceptions.*;
import kg.megalab.newsportal.model.*;
import kg.megalab.newsportal.repository.CategoriesRepository;
import kg.megalab.newsportal.repository.CommentsRepository;
import kg.megalab.newsportal.repository.FavouriteNewsRepository;
import kg.megalab.newsportal.repository.NewsRepository;
import kg.megalab.newsportal.service.AuthService;
import kg.megalab.newsportal.service.FileStorageService;
import kg.megalab.newsportal.service.NewsService;
import kg.megalab.newsportal.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final CategoriesRepository categoriesRepository;
    private final FavouriteNewsRepository favouriteNewsRepository;
    private final CommentsRepository commentsRepository;
    private final TokenService tokenService;
    private final AuthService authService;
    private final FileStorageService fileStorageService;

    @Override
    public Page<NewsDto> getALlNews(List<Integer> categories, String search, Pageable pageable) {
        return newsRepository.findAll(categories, search, tokenService.getUserIdFromToken(), pageable);
    }

    @Override
    public DetailedNewsDto getNewsById(int id) {
        return newsRepository.findById(id, tokenService.getUserIdFromToken());
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> createNews(NewsRequest newsRequest) {
        Category category = categoriesRepository.findById(newsRequest.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException("The category with such id doesn't exist"));

        News news = new News(newsRequest.getTitle(), newsRequest.getShortDescription(),
                newsRequest.getText(), category, LocalDate.now(), authService.getAuthenticatedUser());

        newsRepository.save(news);
        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> uploadNewsImage(int id, MultipartFile file) {
        findNewsById(id);

        News news = newsRepository.findByIdAndUser(id, authService.getAuthenticatedUser())
                .orElseThrow(() -> new UserNotAuthorException("You are not the author of the news"));

        if (news.getImage() != null)
            fileStorageService.deleteImage(news.getImage());

        String fullFileName = fileStorageService.uploadImage(file, "news/");

        news.setImage(fullFileName);
        newsRepository.save(news);

        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public Page<NewsDto> getUserFavouriteNews(String search, Pageable pageable) {
        return newsRepository.findUserFavouriteNews(search, tokenService.getUserIdFromToken(), pageable);
    }

    private News findNewsById(int id) {
        return newsRepository.findById(id).orElseThrow(
                () -> new NewsNotFoundException("The news with such id doesn't exist"));
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> addNewsToFavourites(int id) {
        findNewsById(id);
        int userId = tokenService.getUserIdFromToken();

        if (favouriteNewsRepository.findByUserIdAndNewsId(userId, id).isPresent())
            throw new NewsAlreadyFavouriteException("This news has already been liked");

        favouriteNewsRepository.save(new FavouriteNews(userId, id));
        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> deleteNewsFromFavourites(int id) {
        findNewsById(id);

        FavouriteNews favouriteNews = new FavouriteNews(tokenService.getUserIdFromToken(), id);
        favouriteNewsRepository.delete(favouriteNews);

        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    @Transactional
    public ResponseEntity<SuccessStatusResponse> deleteNewsById(int id) {
        findNewsById(id);

        News news = newsRepository.findByIdAndUser(id, authService.getAuthenticatedUser())
                .orElseThrow(() -> new UserNotAuthorException("You are not the author of the news"));

        favouriteNewsRepository.deleteByNewsId(id);
        newsRepository.deleteById(id);

        if (news.getImage() != null)
            fileStorageService.deleteImage(news.getImage());

        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> writeComment(int newsId, CommentRequest commentRequest) {
        News news = findNewsById(newsId);
        Comment comment = new Comment(commentRequest.getText(), LocalDate.now(),
                authService.getAuthenticatedUser(), news, null);

        commentsRepository.save(comment);
        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> replyToComment(int newsId, int commentId, CommentRequest commentRequest) {
        News news = findNewsById(newsId);

        Comment parentComment = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("The comment with such id doesn't exist"));

        Comment comment = new Comment(commentRequest.getText(), LocalDate.now(),
                authService.getAuthenticatedUser(), news, parentComment);

        commentsRepository.save(comment);
        return ResponseEntity.ok(new SuccessStatusResponse());
    }
}
