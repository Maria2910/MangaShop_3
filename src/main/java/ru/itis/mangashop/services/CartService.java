package ru.itis.mangashop.services;

import ru.itis.mangashop.entities.CartItem;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.User;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    void addToCart(User user, Manga manga, Integer quantity);
    void removeFromCart(User user, Manga manga);
    List<CartItem> getCartItems(User user);
    int getCartItemsCount(User user);
    BigDecimal getCartTotal(User user);
    void clearCart(User user);
    void updateCartItemQuantity(CartItem cartItem, Integer quantity);
    CartItem getCartItemById(Long cartItemId);
}
