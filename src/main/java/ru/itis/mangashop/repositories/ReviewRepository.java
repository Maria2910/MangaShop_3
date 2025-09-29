package ru.itis.mangashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.Review;
import ru.itis.mangashop.entities.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMangaOrderByCreatedAtDesc(Manga manga);
    boolean existsByMangaAndUser(Manga manga, User user);
}
