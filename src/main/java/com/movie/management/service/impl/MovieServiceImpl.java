package com.movie.management.service.impl;

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
    public List<Movie> findAll() {
        return movieDAO.findAll();
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
