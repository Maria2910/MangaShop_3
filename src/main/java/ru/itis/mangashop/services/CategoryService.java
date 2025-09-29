package ru.itis.mangashop.services;

import ru.itis.mangashop.entities.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
}