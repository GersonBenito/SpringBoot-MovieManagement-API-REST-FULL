package com.movie.management.services;

import com.movie.management.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreService {
    public List<Genre> findAll();
    public Optional<Genre> findById(Long id);
    public void save(Genre genre);
    public void deleteById(Long id);
}
