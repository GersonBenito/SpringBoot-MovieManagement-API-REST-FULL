package com.movie.management.persistence.impl;

import com.movie.management.controller.DTO.MultiparamMovieDTO;
import com.movie.management.entity.Genre;
import com.movie.management.entity.Movie;
import com.movie.management.entity.Stock;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.repository.IMovieRepository;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MovieDAOImpl implements IMovieDAO{

    @Autowired
    private IMovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return (List<Movie>) movieRepository.findAll();
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
    
	public Specification<Movie> getAndSpec(Map<String, String> searchParamMap) {

		Set<Field> set = new HashSet<>();
		// get all allowed search fields
		for (Field field : MultiparamMovieDTO.class.getDeclaredFields())
			set.add(field);
		Map<String, Field> fieldIndex = set.stream().collect(Collectors.toMap(Field::getName, Function.identity()));

		// check for incorrect param
		for (Map.Entry<String, String> entry : searchParamMap.entrySet()) {
			if (!"any".equalsIgnoreCase(entry.getKey()) && fieldIndex.get(entry.getKey()) == null) // valid param
				throw new IllegalArgumentException("Bad params");
		}
		
		return (root, query, builder) -> {
			Predicate result = builder.isNotNull(root.get("id"));
			
			for (Map.Entry<String, String> entry : searchParamMap.entrySet()) {
				if (fieldIndex.get(entry.getKey()).getType() == String.class) {
//					result = builder.and(result, builder.equal(root.get(entry.getKey()), entry.getValue().toUpperCase()));
					Expression<String> titleExpression = builder.upper(root.get(entry.getKey()));
					// Use the like condition with a uppercase search string
					result = builder.and(result,
							builder.like(titleExpression, "%" + entry.getValue().toUpperCase() + "%"));
				} else if (fieldIndex.get(entry.getKey()).getType() == Boolean.class) {
					result = builder.and(result,
							builder.equal(root.get(entry.getKey()), ("true".equals(entry.getValue()) ? 1 : 0)));
				} else if (fieldIndex.get(entry.getKey()).getType() == Set.class
						|| fieldIndex.get(entry.getKey()).getType() == List.class) {
					
					 Join<Movie, Genre> genreJoin = fieldIndex.get(entry.getKey()).getType() == Set.class ? root.joinSet(entry.getKey()) : root.joinList(entry.getKey());					 
					 Set<Long> genreIds = Arrays.stream(entry.getValue().split(","))
					            .map(Long::parseLong)
					            .collect(Collectors.toSet());

		                Predicate genrePredicate = genreJoin.get("id").in(genreIds);
		                result = builder.and(result, genrePredicate);
					
				} else if(fieldIndex.get(entry.getKey()).getType() == Stock.class) {
					 Join<Movie, Stock> stockJoin = root.join("stock", JoinType.INNER);
		                Predicate stockPredicate = builder.equal(stockJoin.get("id"), Long.parseLong(entry.getValue()));
		                result = builder.and(result, stockPredicate);
				} else {
					result = builder.and(result, builder.equal(root.get(entry.getKey()), entry.getValue()));
				}
			}
				return result;
		};
	}
}
