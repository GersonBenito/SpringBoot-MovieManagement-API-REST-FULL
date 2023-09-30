package com.movie.management.persistence.impl;

import com.movie.management.entities.Genre;
import com.movie.management.persistence.IGenreDAO;
import com.movie.management.repositories.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDAOImpl implements IGenreDAO {

    @Autowired
    private IGenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return (List<Genre>) genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
