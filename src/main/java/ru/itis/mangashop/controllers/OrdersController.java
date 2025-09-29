package ru.itis.mangashop.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.mangashop.entities.Order;
import ru.itis.mangashop.entities.User;
import ru.itis.mangashop.services.OrderService;
import ru.itis.mangashop.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public String getUserOrders(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<Order> orders = orderService.getUserOrders(user);
        model.addAttribute("orders", orders);
        return "orders";
    }
}
