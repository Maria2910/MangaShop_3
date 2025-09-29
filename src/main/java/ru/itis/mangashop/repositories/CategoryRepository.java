package ru.itis.mangashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.mangashop.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
