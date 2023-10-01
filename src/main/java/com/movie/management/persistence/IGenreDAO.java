package com.movie.management.persistence;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Genre;

public interface IGenreDAO {
    public List<Genre> findAll();
    public Optional<Genre> findById(Long id);
    public void save(Genre genre);
    public void deleteById(Long id);
}
