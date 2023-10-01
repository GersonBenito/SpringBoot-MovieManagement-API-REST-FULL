package com.movie.management.util;

import org.springframework.stereotype.Component;

import com.movie.management.controller.DTO.GenreDTO;
import com.movie.management.controller.DTO.MovieDTO;

@Component
public class ValidFieldUtil {
    public boolean isInvalidField(GenreDTO genreDTO){
        return genreDTO.getName().isBlank();
    }

    public boolean isInvalidField(MovieDTO movieDTO){
        return movieDTO.getTitle().isBlank() || movieDTO.getDescription().isBlank() || movieDTO.getPosterImage().isBlank() || movieDTO.getBackgroundImage().isBlank();
    }
}
