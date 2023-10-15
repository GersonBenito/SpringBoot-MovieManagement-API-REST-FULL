package com.movie.management.controller;

import com.movie.management.controller.DTO.UserEntityDTO;
import com.movie.management.entity.RoleEntity;
import com.movie.management.entity.UserEntity;
import com.movie.management.repository.IUserRepository;
import com.movie.management.service.IEmailService;
import com.movie.management.util.ERole;
import com.movie.management.util.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private IEmailService emailService;

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

    @PostMapping("/find")
    public ResponseEntity<?>findUserByEmail(@RequestParam String email){
        Map<String, Object> response = new HashMap<>();
        if(email.isBlank()){
            response.put("message", "Param is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> userEntityOptional = userRepository.findUserByEmail(email);

        if(userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            Map<String, Object> user = new HashMap<>();
            String token = jwtUtils.generateAccessToken(userEntity.getUsername());
            //user.put("id", userEntity.getId());
            user.put("email", userEntity.getEmail());
            user.put("token", token);

            response.put("Data", user);
            response.put("message", "User find success");

            emailService.sendEmail(
                    userEntity.getEmail(),
                    "Recover password",
                    "Hello! "
                            .concat(userEntity.getUsername())
                            .concat(" Para recuperar tu usuario ingresa al siguiente link")
                            .concat(" http://localhost:8080/api/v1/auth/send-recover?token=")
                            .concat(token)
            );
            return ResponseEntity.ok(response);
        }

        response.put("message", "User with email "+email+" not found");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/send-recover")
    public ResponseEntity<?> recoverUser(@RequestParam String token){
        // send to url frontend
        Map<String, Object> response = new HashMap<>();
        if(token.isBlank()){
            response.put("message", "Token is null");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("Data", new HashMap<>());
        response.put("message", "Endpoint in progress");
        return ResponseEntity.ok(response);
    }
}
