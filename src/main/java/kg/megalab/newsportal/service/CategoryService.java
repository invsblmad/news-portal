package kg.megalab.newsportal.service;

import kg.megalab.newsportal.dto.response.data.CategoryView;

import java.util.List;

public interface CategoryService {
    List<CategoryView> getCategories();
}
