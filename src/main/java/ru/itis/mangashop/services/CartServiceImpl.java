package ru.itis.mangashop.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.mangashop.entities.CartItem;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.repositories.CartItemRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public void addToCart(User user, Manga manga, Integer quantity) {
        Optional<CartItem> existingItem = cartItemRepository.findByUserAndManga(user, manga);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setManga(manga);
            newItem.setQuantity(quantity);
            newItem.setPrice(manga.getPrice()); // Устанавливаем текущую цену
            cartItemRepository.save(newItem);
        }
    }

    @Override
    @Transactional
    public void removeFromCart(User user, Manga manga) {
        cartItemRepository.deleteByUserAndManga(user, manga);
    }

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    public int getCartItemsCount(User user) {
        return cartItemRepository.countByUser(user);
    }

    @Override
    public BigDecimal getCartTotal(User user) {
        List<CartItem> items = getCartItems(user);
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        List<CartItem> items = getCartItems(user);
        cartItemRepository.deleteAll(items);
    }

    @Override
    @Transactional
    public void updateCartItemQuantity(CartItem cartItem, Integer quantity) {
        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
    }
}
