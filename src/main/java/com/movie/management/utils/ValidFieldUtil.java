package com.movie.management.utils;

import com.movie.management.controllers.DTO.GenreDTO;
import org.springframework.stereotype.Component;

@Component
public class ValidFieldUtil {
    public boolean isValidField(GenreDTO genreDTO){
        return genreDTO.getName().isBlank();
    }
}
