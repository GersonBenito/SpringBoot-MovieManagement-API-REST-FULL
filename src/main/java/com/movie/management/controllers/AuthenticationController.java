package com.movie.management.controllers;

import com.movie.management.controllers.DTO.UserEntityDTO;
import com.movie.management.entities.RoleEntity;
import com.movie.management.entities.UserEntity;
import com.movie.management.repositories.IUserRepository;
import com.movie.management.utils.ERole;
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
                .password(passwordEncoder.encode(userEntityDTO.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        Map<String, Object> userData = new HashMap<>();

        userData.put("id", userEntity.getId());
        userData.put("email", userEntity.getEmail());
        userData.put("username", userEntity.getUsername());
        userData.put("roles", userEntity.getRoles());

        response.put("message", "Usuario creado");
        response.put("Data", userData);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> hello(){
        Map<String, String> response = new HashMap<>();

        response.put("message", "Access endpoint hello");

        return ResponseEntity.ok(response);
    }
}
