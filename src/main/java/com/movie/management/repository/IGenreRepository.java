package com.movie.management.repository;

import com.movie.management.entity.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.management.entity.Genre;

@Repository
public interface IGenreRepository extends CrudRepository<Genre, Long> {
}