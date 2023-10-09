package com.movie.management.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.movie.management.entity.Movie;

public interface IMovieDAO {
    public List<Movie> findAll();
    public Optional<Movie> findById(Long id);
    public void save(Movie movie);
    public void deleteById(Long id);
	public Specification<Movie> getAndSpec(Map<String, String> searchParamMap);
}
