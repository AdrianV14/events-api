package com.gestion.eventos.api.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gestion.eventos.api.security.jwt.JwtAuthEntryPoint;
import com.gestion.eventos.api.security.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity 
public class SecurityConfig {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Cadena de filtros de spring security, el orden en el que se llaman no es el orden en el que necesariamente se ejecutan
        http
                .csrf(AbstractHttpConfigurer::disable)  // deshabilita el uso de CSRF token ya que es inecesario con JWT
                .exceptionHandling(exception -> 
                        exception.authenticationEntryPoint(jwtAuthEntryPoint)
                    )   // Configura como Spring gestiona las exceptions relacionadas con seguridad
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )   // Configura como Spring security maneja las peticiones Http
                .authorizeHttpRequests(auth -> 
                    auth
                        .requestMatchers("/api/v1/auth/**").permitAll() //Todas las rutas Auth estan habilitadas para poder autenticarnos
                        .requestMatchers("/h2-console/**").permitAll() //Todas las rutas Auth estan habilitadas para poder autenticarnos
                        .anyRequest().authenticated()   // El resto necesita autenticacion
                )
                .headers(AbstractHttpConfigurer::disable);  //Configuracion necesaria para el uso de H2
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  //Inserta un filtro personalizado

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

}
