package ru.itis.mangashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.mangashop.entities.Manga;
import java.util.List;

public interface MangaRepository extends JpaRepository<Manga, Long> {

    @Query("SELECT DISTINCT m FROM Manga m LEFT JOIN FETCH m.categories c WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(m.author) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:categoryId IS NULL OR c.id = :categoryId)")
    List<Manga> findMangas(@Param("search") String search,
                           @Param("categoryId") Long categoryId);
}