package com.movie.management.service;

import java.util.List;
import java.util.Optional;

import com.movie.management.controller.DTO.MovieDTO;
import com.movie.management.entity.Movie;

public interface IMovieService {
    public List<MovieDTO> findAll();
    public Optional<MovieDTO> findById(Long id);
    public void save(MovieDTO movie);
    public void deleteById(Long id);
}
