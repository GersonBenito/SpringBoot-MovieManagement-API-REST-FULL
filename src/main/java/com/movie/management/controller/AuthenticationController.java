package com.movie.management.controller;

import com.movie.management.controller.DTO.UserEntityDTO;
import com.movie.management.entity.RoleEntity;
import com.movie.management.entity.UserEntity;
import com.movie.management.repository.IUserRepository;
import com.movie.management.util.ERole;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserEntityDTO userEntityDTO){
        Map<String, Object> response = new HashMap<>();

        Set<RoleEntity> roles = userEntityDTO.getRoles()
                .stream().map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build()
                ).collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .email(userEntityDTO.getEmail())
                .username(userEntityDTO.getUsername())
                .image(userEntityDTO.getImage())
                .password(passwordEncoder.encode(userEntityDTO.getPassword()))
                .roles(roles)
                .movies(userEntityDTO.getMovies())
                .build();

        userRepository.save(userEntity);

        response.put("message", "Usuario registrado");

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> hello(){
        Map<String, String> response = new HashMap<>();

        response.put("message", "Access endpoint hello");

        return ResponseEntity.ok(response);
    }
}
