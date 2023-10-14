package com.movie.management.service.impl;

import com.movie.management.entity.Movie;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.service.IMovieService;
import com.movie.management.util.EType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieDAO movieDAO;

	@Override
	public List<Movie> findAll() {
		return movieDAO.findAll();
	}

	@Override
	public List<Movie> searchByParam(String param) {
		return movieDAO.searchByParam(param);
	}

	@Override
	public List<Movie> filterByRangePrice(BigDecimal minPrice, BigDecimal maxPrice, EType type) {
		if(type == EType.PURCHASE){
			return movieDAO.filterByRangePurchasePrice(minPrice, maxPrice);
		}else{
			return movieDAO.filterByRangeRentalPrice(minPrice, maxPrice);
		}
	}

	@Override
	public List<Movie> filterByGenre(String genre) {
		return movieDAO.filterByGenre(genre);
	}

	@Override
	public Optional<Movie> findById(Long id) {
		return movieDAO.findById(id);
	}

	@Override
	public void save(Movie movie) {
		movieDAO.save(movie);
	}

	@Override
	public void deleteById(Long id) {
		movieDAO.deleteById(id);
	}
}