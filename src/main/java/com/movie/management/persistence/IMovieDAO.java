package com.movie.management.persistence;

import com.movie.management.entity.Movie;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.movie.management.entity.Movie;

public interface IMovieDAO {
    public List<Movie> findAll();
    public List<Movie> searchByParam(String param);
    public List<Movie> filterByRangeRentalPrice(BigDecimal minPrice, BigDecimal maxPrice);
    public List<Movie> filterByRangePurchasePrice(BigDecimal minPrice, BigDecimal maxPrice);
    public List<Movie> filterByGenre(String genre);
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
}