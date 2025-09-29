package ru.itis.mangashop.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private String username;
    private String password;
}
