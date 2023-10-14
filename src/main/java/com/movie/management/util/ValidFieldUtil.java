package com.movie.management.util;

import com.movie.management.controller.DTO.GenreDTO;
import com.movie.management.controller.DTO.MovieDTO;
import org.springframework.stereotype.Component;

@Component
public class ValidFieldUtil {
    public boolean isValidField(GenreDTO genreDTO){
        return genreDTO.getName().isBlank();
    }

    public boolean isValidField(MovieDTO movieDTO){
        return movieDTO.getTitle().isBlank()
                || movieDTO.getDescription().isBlank()
                || movieDTO.getPosterImage().isBlank()
                || movieDTO.getBackgroundImage().isBlank()
                || movieDTO.getRentalPrice() == null
                || movieDTO.getPurchasePrice() == null;
    }
}
