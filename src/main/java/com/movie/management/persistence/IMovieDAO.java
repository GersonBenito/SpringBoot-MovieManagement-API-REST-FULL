package com.movie.management.persistence;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Movie;

public interface IMovieDAO {
    public List<Movie> findAll();
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
}
