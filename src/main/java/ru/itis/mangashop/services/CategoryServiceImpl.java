package ru.itis.mangashop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.mangashop.entities.Category;
import ru.itis.mangashop.repositories.CategoryRepository;
import java.util.List;

@Service
@RequiredArgsConstructor // ← добавляем аннотацию
public class CategoryServiceImpl implements CategoryService { // ← реализуем интерфейс

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}