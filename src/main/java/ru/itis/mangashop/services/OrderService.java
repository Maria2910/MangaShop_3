package ru.itis.mangashop.services;

import ru.itis.mangashop.dto.OrderRequest;
import ru.itis.mangashop.entities.Order;
import ru.itis.mangashop.entities.OrderStatus;
import ru.itis.mangashop.entities.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderRequest orderRequest);
    List<Order> getUserOrders(User user);
    Order getOrderById(Long id);
    List<Order> getOrdersByStatus(OrderStatus status);
    void updateOrderStatus(Long orderId, OrderStatus status);
}
