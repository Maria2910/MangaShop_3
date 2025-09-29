package ru.itis.mangashop.services;

import ru.itis.mangashop.dto.UserUpdateRequest;
import ru.itis.mangashop.entities.User;

public interface UserService {

    void registerUser(String username, String email, String password);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void saveUser(User user);
    void updateUserProfile(String username, UserUpdateRequest updateRequest);
    void assignRoleToUser(Long userId, String roleName);
    void removeRoleFromUser(Long userId, String roleName);
    boolean userHasRole(Long userId, String roleName);
}