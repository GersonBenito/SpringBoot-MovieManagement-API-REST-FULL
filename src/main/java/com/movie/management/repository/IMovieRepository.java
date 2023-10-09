package com.movie.management.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.management.entity.Movie;

@Repository
public interface IMovieRepository extends CrudRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    // TODO: Agregar filtros de peliculas, por categoria y busqueda de multiparametros
	}
