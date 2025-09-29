package ru.itis.mangashop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.mangashop.dto.OrderRequest;
import ru.itis.mangashop.entities.*;
import ru.itis.mangashop.repositories.OrderItemRepository;
import ru.itis.mangashop.repositories.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MangaService mangaService;
    private final CartService cartService;

    @Override
    @Transactional
    public Order createOrder(User user, OrderRequest orderRequest) {
        // Получаем товары из корзины
        List<CartItem> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Корзина пуста");
        }

        // Создаем заказ
        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .totalAmount(cartService.getCartTotal(user))
                .status(OrderStatus.PENDING)
                .customerName(orderRequest.getCustomerName())
                .customerEmail(orderRequest.getCustomerEmail())
                .customerPhone(orderRequest.getCustomerPhone())
                .shippingAddress(orderRequest.getShippingAddress())
                .paymentMethod(orderRequest.getPaymentMethod())
                .build();

        order = orderRepository.save(order);

        // Создаем элементы заказа и обновляем количество товаров
        for (CartItem cartItem : cartItems) {
            Manga manga = cartItem.getManga();

            // Проверяем доступное количество
            if (manga.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalStateException("Недостаточно товара: " + manga.getTitle());
            }

            // Обновляем количество товара
            manga.setStockQuantity(manga.getStockQuantity() - cartItem.getQuantity());
            mangaService.saveManga(manga);

            // Создаем элемент заказа
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .manga(manga)
                    .quantity(cartItem.getQuantity())
                    .price(manga.getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }

        // Очищаем корзину
        cartService.clearCart(user);

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserWithItemsAndManga(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findByIdWithItemsAndManga(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatusWithItemsAndManga(status);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}