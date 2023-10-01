package com.movie.management.util;

import org.springframework.stereotype.Component;

import com.movie.management.controller.DTO.GenreDTO;
import com.movie.management.controller.DTO.MovieDTO;

@Component
public class ValidFieldUtil {
    public boolean isValidField(GenreDTO genreDTO){
        return genreDTO.getName().isBlank();
    }

    public boolean isValidField(MovieDTO movieDTO){
        return movieDTO.getTitle().isBlank() || movieDTO.getDescription().isBlank() || movieDTO.getPosterImage().isBlank() || movieDTO.getBackgroundImage().isBlank();
    }
}
