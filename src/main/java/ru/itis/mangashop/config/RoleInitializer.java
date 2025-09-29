package ru.itis.mangashop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.itis.mangashop.entities.Role;
import ru.itis.mangashop.repositories.RoleRepository;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("✅ RoleInitializer запущен!");

        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");

        System.out.println("✅ Роли инициализированы");
    }

    private void createRoleIfNotFound(String name) {
        if (!roleRepository.existsByName(name)) {
            Role role = new Role(name);
            roleRepository.save(role);
            System.out.println("✅ Создана роль: " + name);
        } else {
            System.out.println("✅ Роль уже существует: " + name);
        }
    }
}