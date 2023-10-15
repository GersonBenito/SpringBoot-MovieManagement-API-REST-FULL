package com.movie.management.security;

import com.movie.management.security.filter.JwtAuthenticationFilter;
import com.movie.management.security.filter.JwtAuthorizationFilter;
import com.movie.management.service.UserDetailServiceImpl;
import com.movie.management.util.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtAuthorizationFilter authorizationFilter;

    // metodo que configura la cadena de filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);

        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login"); // cambiar la url por defecto que maneja spring security, por defecto es /login

        return httpSecurity.csrf(config -> config.disable()) // desabilitar crosside para el manejo de formularios desde el frontend
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/**").permitAll(); // permitir que todos los usuarios puedan consumir este endpoint
                    auth.anyRequest().authenticated(); // cualquier otro request diferete de /api/vi/register debe de estar autenticado
                })
                .sessionManagement(session -> { // administrador de las sesiones
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter) // agregar el primer filtro
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class) // se debe de ejecutar antes del primer filtro
                .build();
    }

    // encriptar contraseña
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}
