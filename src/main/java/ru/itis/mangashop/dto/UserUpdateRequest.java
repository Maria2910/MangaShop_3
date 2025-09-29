package ru.itis.mangashop.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bio;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String postalCode;
}
