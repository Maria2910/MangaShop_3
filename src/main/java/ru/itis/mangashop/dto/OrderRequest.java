package ru.itis.mangashop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message = "ФИО обязательно")
    private String customerName;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String customerEmail;

    @NotBlank(message = "Телефон обязателен")
    private String customerPhone;

    @NotBlank(message = "Адрес доставки обязателен")
    private String shippingAddress;

    @NotBlank(message = "Способ оплаты обязателен")
    private String paymentMethod;
}