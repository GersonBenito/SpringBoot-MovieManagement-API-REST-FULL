package com.movie.management.service;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Movie;

public interface IMovieService {
    public List<Movie> findAll();
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
}
