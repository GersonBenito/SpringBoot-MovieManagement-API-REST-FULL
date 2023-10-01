package com.movie.management.controllers.DTO;

import com.movie.management.entities.Movie;
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
