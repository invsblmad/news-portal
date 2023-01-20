package kg.megalab.newsportal.service.impl;

import kg.megalab.newsportal.dto.response.data.CategoryView;
import kg.megalab.newsportal.repository.CategoriesRepository;
import kg.megalab.newsportal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

    @Override
    public List<CategoryView> getCategories() {
        return categoriesRepository.findBy();
    }
}
