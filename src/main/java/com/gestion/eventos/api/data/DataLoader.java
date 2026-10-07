package com.gestion.eventos.api.data;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gestion.eventos.api.domain.Role;
import com.gestion.eventos.api.domain.Speaker;
import com.gestion.eventos.api.domain.User;
import com.gestion.eventos.api.domain.Category;
import com.gestion.eventos.api.repository.IRoleRepository;
import com.gestion.eventos.api.repository.ISpeakerRepository;
import com.gestion.eventos.api.repository.IUserRepository;
import com.gestion.eventos.api.repository.ICategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ICategoryRepository categoryReponsitory;
    private final ISpeakerRepository speakerRepository;

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

        if (userRepository.findByUsername("user").isEmpty()) {
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

        if(!categoryReponsitory.existsByName("Conferencia")){
            Category conferencia = new Category(null, "Conferencia", "Eventos de gran escala con múltiples oradores.");
            categoryReponsitory.save(conferencia);
        }
        if(!categoryReponsitory.existsByName("Taller")){
            Category taller = new Category(null, "Taller", "Eventos interactivos y prácticos.");
            categoryReponsitory.save(taller);
        }
        if(!categoryReponsitory.existsByName("Webinar")){
            Category webinar = new Category(null, "Webinar", "Seminario online en vivo.");
            categoryReponsitory.save(webinar);
        }
        

        if(!speakerRepository.existsByEmail("john.doe@example.com")){
            Speaker john = new Speaker(null, "John Doe", "john.doe@example.com", "Experto en desarrollo de software.", new HashSet<>());
            speakerRepository.save(john);
        }
        if(!speakerRepository.existsByEmail("jane.smith@example.com")){
            Speaker jane = new Speaker(null, "Jane Smith", "jane.smith@example.com", "Especialista en marketing digital.", new HashSet<>());
            speakerRepository.save(jane);
        }
        
    }

}
