package ru.itis.mangashop.entities;

public enum OrderStatus {
    PENDING,       // В обработке
    PROCESSING,    // Обрабатывается
    SHIPPED,       // Отправлен
    DELIVERED,     // Доставлен
    CANCELLED      // Отменен
}
