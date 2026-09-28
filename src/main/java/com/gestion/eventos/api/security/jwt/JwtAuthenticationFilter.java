package com.gestion.eventos.api.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;




@Component
@RequiredArgsConstructor 
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtGenerator jwtGenerator;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {       
        String token = getJwtFromRequest(request);  // Tomo el token de la request

        // Verifico si el token tiene texto, es valido, esta autenticado
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtGenerator.getUsernameFromJwt(token);   // Tomo el username del token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // Hago la busqueda por el nombre de usuario

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
            );  // Objeto de autenticacion que representa el usuario logeado en spring security

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Agrega informacion adicional al token de autenticacion
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // Guardamos el usuario autenticado en el contexto de spring security

        }
        filterChain.doFilter(request, response);    // Continuamos con los siguientes filtros
    }

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
