package com.movie.management.persistence.impl;

import com.movie.management.entity.Movie;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.repository.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class MovieDAOImpl implements IMovieDAO{

	@Autowired
	private IMovieRepository movieRepository;

	@Override
	public List<Movie> findAll() {
		return (List<Movie>) movieRepository.findAll();
	}

	@Override
	public List<Movie> searchByParam(String param) {
		return movieRepository.searchByParam(param);
	}

	@Override
	public List<Movie> filterByRangeRentalPrice(BigDecimal minPrice, BigDecimal maxPrice) {
		return movieRepository.filterByRangeRentalPrice(minPrice, maxPrice);
	}

	@Override
	public List<Movie> filterByRangePurchasePrice(BigDecimal minPrice, BigDecimal maxPrice) {
		return movieRepository.filterByRangePurchasePrice(minPrice, maxPrice);
	}

	@Override
	public List<Movie> filterByGenre(String genre) {
		return movieRepository.filterByGenre(genre);
	}

	@Override
	public Optional<Movie> findById(Long id) {
		return movieRepository.findById(id);
	}

	@Override
	public void save(Movie movie) {
		movieRepository.save(movie);
	}

	@Override
	public void deleteById(Long id) {
		movieRepository.deleteById(id);
	}
}
