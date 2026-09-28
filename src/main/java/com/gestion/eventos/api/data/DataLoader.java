package com.gestion.eventos.api.data;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gestion.eventos.api.domain.Role;
import com.gestion.eventos.api.domain.User;
import com.gestion.eventos.api.repository.IRoleRepository;
import com.gestion.eventos.api.repository.IUserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ROLE_ADMIN");
            return roleRepository.save(newRole);
        });

        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("ROLE_USER");
            return roleRepository.save(newRole);
        });

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setName("Administrador");
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin1234"));

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            adminRoles.add(userRole);

            admin.setRoles(adminRoles);

            userRepository.save(admin);
        }

        if(userRepository.findByUsername("user").isEmpty()){
            User user = new User();
            user.setName("Usuario normal");
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user1234"));

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            user.setRoles(userRoles);

            userRepository.save(user);
        }
    }

}
