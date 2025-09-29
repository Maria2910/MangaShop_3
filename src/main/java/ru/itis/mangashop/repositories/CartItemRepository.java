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

    // Найти товар в корзине пользователя
    Optional<CartItem> findByUserAndManga(User user, Manga manga);

    // Все товары в корзине пользователя с сортировкой
    @Query("SELECT ci FROM CartItem ci WHERE ci.user = :user ORDER BY ci.createdAt DESC, ci.id DESC")
    List<CartItem> findByUser(@Param("user") User user);

    // Количество товаров в корзине пользователя
    int countByUser(User user);

    // Удалить товар из корзины пользователя
    void deleteByUserAndManga(User user, Manga manga);
}