package com.movie.management.service;

import com.movie.management.entity.Movie;
import com.movie.management.util.EType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IMovieService {
    public List<Movie> findAll();
    public List<Movie> searchByParam(String param);
    public List<Movie> filterByRangePrice(BigDecimal minPrice, BigDecimal maxPrice, EType type);
    public List<Movie> filterByGenre(String genre);
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
}