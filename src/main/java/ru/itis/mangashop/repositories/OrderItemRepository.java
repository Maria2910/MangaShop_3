package ru.itis.mangashop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.mangashop.entities.Order;
import ru.itis.mangashop.entities.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
