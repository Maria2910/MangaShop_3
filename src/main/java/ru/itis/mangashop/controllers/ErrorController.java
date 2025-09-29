package ru.itis.mangashop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode != null) {
            String message = switch (statusCode) {
                case 400 -> "Неверный запрос";
                case 403 -> "Доступ запрещен";
                case 404 -> "Страница не найдена";
                case 500 -> "Внутренняя ошибка сервера";
                default -> "Ошибка " + statusCode;
            };
            model.addAttribute("error", message);
        }

        return "error";
    }
}