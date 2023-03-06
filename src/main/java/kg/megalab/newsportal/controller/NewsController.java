package kg.megalab.newsportal.controller;

import kg.megalab.newsportal.dto.request.CommentRequest;
import kg.megalab.newsportal.dto.request.NewsRequest;
import kg.megalab.newsportal.dto.response.data.DetailedNewsDto;
import kg.megalab.newsportal.dto.response.data.NewsDto;
import kg.megalab.newsportal.dto.response.status.SuccessStatusResponse;
import kg.megalab.newsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Page<NewsDto> getAllNews(
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) String search, Pageable pageable
    ) {
        return newsService.getALlNews(categories, search, pageable);
    }

    @GetMapping("/favourites")
    public Page<NewsDto> getUserFavouriteNews(
            @RequestParam(required = false) String search, Pageable pageable
    ) {
        return newsService.getUserFavouriteNews(search, pageable);
    }

    @PostMapping
    public ResponseEntity<SuccessStatusResponse> createNews(@Valid @RequestBody NewsRequest newsRequest) {
        return newsService.createNews(newsRequest);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<SuccessStatusResponse> uploadNewsImage(
            @PathVariable int id, @RequestPart("file") MultipartFile multipartFile
    ) {
        return newsService.uploadNewsImage(id, multipartFile);
    }

    @GetMapping("/{id}")
    public DetailedNewsDto getNewsById(@PathVariable int id) {
        return newsService.getNewsById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessStatusResponse> deleteNewsById(@PathVariable int id) {
        return newsService.deleteNewsById(id);
    }

    @PostMapping("/{id}/favourites")
    public ResponseEntity<SuccessStatusResponse> addNewsToFavourites(@PathVariable int id) {
        return newsService.addNewsToFavourites(id);
    }

    @DeleteMapping("/{id}/favourites")
    public ResponseEntity<SuccessStatusResponse> deleteNewsFromFavourites(@PathVariable int id) {
        return newsService.deleteNewsFromFavourites(id);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<SuccessStatusResponse> writeComment(
            @PathVariable int id, @Valid @RequestBody CommentRequest commentRequest
    ) {
        return newsService.writeComment(id, commentRequest);
    }

    @PostMapping("/{newsId}/comments/{commentId}/reply")
    public ResponseEntity<SuccessStatusResponse> replyToComment(
            @PathVariable int newsId, @PathVariable int commentId,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        return newsService.replyToComment(newsId, commentId, commentRequest);
    }

}
