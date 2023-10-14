package com.movie.management.service;

import com.movie.management.entity.Genre;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Genre;

public interface IGenreService {
    public List<Genre> findAll();
    public Optional<Genre> findById(Long id);
    public void save(Genre genre);
    public void deleteById(Long id);
}