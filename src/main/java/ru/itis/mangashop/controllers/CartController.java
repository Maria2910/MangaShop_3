package ru.itis.mangashop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.mangashop.dto.OrderRequest;
import ru.itis.mangashop.entities.CartItem;
import ru.itis.mangashop.entities.Manga;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.services.CartService;
import ru.itis.mangashop.services.MangaService;
import ru.itis.mangashop.services.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MangaService mangaService;
    private final UserService userService;

    // Добавление в корзину
    @PostMapping("/cart/add/{mangaId}")
    public String addToCart(@PathVariable Long mangaId,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Manga manga = mangaService.getMangaById(mangaId);

        cartService.addToCart(user, manga, quantity);

        return "redirect:/manga/" + mangaId + "?added=true";
    }

    @GetMapping("/cart")
    public String viewCart(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<CartItem> cartItems = cartService.getCartItems(user);
        BigDecimal cartTotal = cartService.getCartTotal(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("cartItemsCount", cartItems.size());
        model.addAttribute("user", user);

        // Добавляем DTO для формы заказа
        model.addAttribute("orderRequest", new OrderRequest());

        return "cart";
    }

    // Обновление количества
    @PostMapping("/cart/update/{cartItemId}")
    public String updateCartItem(@PathVariable Long cartItemId,
                                 @RequestParam Integer quantity,
                                 Principal principal) {
        User user = userService.findByUsername(principal.getName());
        CartItem cartItem = cartService.getCartItemById(cartItemId);

        // Проверяем, что товар принадлежит пользователю
        if (cartItem.getUser().getId().equals(user.getId())) {
            cartService.updateCartItemQuantity(cartItem, quantity);
        }

        return "redirect:/cart";
    }

    // Удаление из корзины
    @PostMapping("/cart/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId,
                                 Principal principal) {
        User user = userService.findByUsername(principal.getName());
        CartItem cartItem = cartService.getCartItemById(cartItemId);

        // Проверяем, что товар принадлежит пользователю
        if (cartItem.getUser().getId().equals(user.getId())) {
            cartService.removeFromCart(user, cartItem.getManga());
        }

        return "redirect:/cart";
    }

    // Очистка корзины
    @PostMapping("/cart/clear")
    public String clearCart(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        cartService.clearCart(user);
        return "redirect:/cart";
    }
}
