package com.movie.management.service.impl;

import com.movie.management.controller.DTO.MovieDTO;
import com.movie.management.entity.Movie;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.service.IMovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private IMovieDAO movieDAO;

    @Override
    public List<MovieDTO> findAll() {
        return movieDAO.findAll().stream().map(movie -> new MovieDTO(movie)).toList();
    }

    @Override
    public Optional<MovieDTO> findById(Long id) {
        return MovieDTO.MovieDTOOptional(movieDAO.findById(id));
    }

    @Override
    public void save(MovieDTO movieDTO) {
        movieDAO.save(Movie.builder()
                .title(movieDTO.getTitle())
                .description(movieDTO.getDescription())
                .posterImage(movieDTO.getPosterImage())
                .backgroundImage(movieDTO.getBackgroundImage())
                .popularity(movieDTO.getPopularity())
                .rentalPrice(movieDTO.getRentalPrice())
                .purchasePrice(movieDTO.getPurchasePrice())
                .availability(movieDTO.isAvailability())
                .stock(movieDTO.getStock())
                .genres(movieDTO.getGenres())
                .build());
    }

    @Override
    public void deleteById(Long id) {
        movieDAO.deleteById(id);
    }
}
