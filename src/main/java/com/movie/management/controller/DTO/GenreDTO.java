package com.movie.management.controller.DTO;

import com.movie.management.entity.Movie;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GenreDTO {
    private Long id;
    private String name;
}
