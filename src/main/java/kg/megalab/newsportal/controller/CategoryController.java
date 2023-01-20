package kg.megalab.newsportal.controller;

import kg.megalab.newsportal.dto.response.data.CategoryView;
import kg.megalab.newsportal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryView> getCategories() {
        return categoryService.getCategories();
    }
}
