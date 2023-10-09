package com.movie.management.controller.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.movie.management.entity.Movie;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntityDTO {

    private Long id;
    
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
    private String image;

    private Set<String> roles;
    
    @Builder.Default
    private List<Movie> movies = new ArrayList<>();
}
