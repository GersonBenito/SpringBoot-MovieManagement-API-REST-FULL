package com.movie.management.repositories;

import com.movie.management.entities.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends CrudRepository<Movie, Long> {
    // TODO: Agregar filtros de peliculas, por categoria y busqueda de multiparametros
}
