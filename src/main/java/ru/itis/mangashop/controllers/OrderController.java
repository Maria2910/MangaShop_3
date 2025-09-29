package ru.itis.mangashop.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.mangashop.dto.OrderRequest;
import ru.itis.mangashop.entities.Order;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.services.OrderService;
import ru.itis.mangashop.services.UserService;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/create")
    public String createOrder(@Valid @ModelAttribute OrderRequest orderRequest,
                              BindingResult bindingResult,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, заполните все поля корректно");
            return "redirect:/cart";
        }

        try {
            User user = userService.findByUsername(principal.getName());
            Order order = orderService.createOrder(user, orderRequest);

            redirectAttributes.addFlashAttribute("success",
                    "Заказ #" + order.getId() + " успешно оформлен!");
            return "redirect:/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable Long id,
                                  Principal principal,
                                  Model model) throws AccessDeniedException {
        User user = userService.findByUsername(principal.getName());
        Order order = orderService.getOrderById(id);

        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Доступ запрещен");
        }

        model.addAttribute("order", order);
        return "order-details";
    }
}