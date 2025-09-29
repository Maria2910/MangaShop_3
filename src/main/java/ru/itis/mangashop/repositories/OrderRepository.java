package ru.itis.mangashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.mangashop.entities.Order;
import ru.itis.mangashop.entities.OrderStatus;
import ru.itis.mangashop.entities.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByOrderDateDesc(User user);
    List<Order> findByStatusOrderByOrderDateDesc(OrderStatus status);

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.manga " +
            "WHERE o.user = :user " +
            "ORDER BY o.orderDate DESC")
    List<Order> findByUserWithItemsAndManga(@Param("user") User user);

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.manga " +
            "WHERE o.id = :id")
    Optional<Order> findByIdWithItemsAndManga(@Param("id") Long id);

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.items i " +
            "LEFT JOIN FETCH i.manga " +
            "WHERE o.status = :status " +
            "ORDER BY o.orderDate DESC")
    List<Order> findByStatusWithItemsAndManga(@Param("status") OrderStatus status);
}