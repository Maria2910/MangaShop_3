package ru.itis.mangashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.mangashop.entities.CartItem;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.User;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserAndManga(User user, Manga manga);

    @Query("SELECT ci FROM CartItem ci WHERE ci.user = :user ORDER BY ci.createdAt DESC, ci.id DESC")
    List<CartItem> findByUser(@Param("user") User user);

    int countByUser(User user);

    void deleteByUserAndManga(User user, Manga manga);
}