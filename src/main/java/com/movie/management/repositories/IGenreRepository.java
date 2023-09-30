package com.movie.management.repositories;

import com.movie.management.entities.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenreRepository extends CrudRepository<Genre, Long> {
}
