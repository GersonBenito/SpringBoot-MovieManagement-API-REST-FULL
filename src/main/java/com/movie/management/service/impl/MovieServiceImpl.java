package com.movie.management.service.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.movie.management.controller.DTO.MovieDTO;
import com.movie.management.controller.DTO.MultiparamMovieDTO;
import com.movie.management.entity.Genre;
import com.movie.management.entity.Movie;
import com.movie.management.entity.Stock;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.repository.IMovieRepository;
import com.movie.management.service.IMovieService;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieDAO movieDAO;

	@Override
	public List<MovieDTO> findAll() {
		return movieDAO.findAll().stream().map(MovieDTO::new).toList();
	}

	@Override
	public Optional<MovieDTO> findById(Long id) {
		return MovieDTO.MovieDTOOptional(movieDAO.findById(id));
	}

	@Override
	public void save(MovieDTO movieDTO) {
		movieDAO.save(Movie.builder().title(movieDTO.getTitle()).description(movieDTO.getDescription())
				.posterImage(movieDTO.getPosterImage()).backgroundImage(movieDTO.getBackgroundImage())
				.popularity(movieDTO.getPopularity()).rentalPrice(movieDTO.getRentalPrice())
				.purchasePrice(movieDTO.getPurchasePrice()).availability(movieDTO.isAvailability())
				.stock(movieDTO.getStock()).genres(movieDTO.getGenres()).build());
	}

	@Override
	public void deleteById(Long id) {
		movieDAO.deleteById(id);
	}
	

	@Override
	public List<MovieDTO> getMoviesCustom(Map<String, String> searchParamMap) {
//		String str = StringUtil.queryMaker(searchParamMap);
		
		searchParamMap.remove("any");
		List<Movie> movies = movieRepo.findAll(movieDAO.getAndSpec(searchParamMap));
//		List<Movie> movies = jdbcTemplate.query(StringUtil.queryMaker(searchParamMap), new MovieMapper());
		return movies.stream().map(MovieDTO::new).toList();
		
	}
}
