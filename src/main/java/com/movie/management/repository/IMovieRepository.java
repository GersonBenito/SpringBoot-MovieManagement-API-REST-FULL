package com.movie.management.repository;

import com.movie.management.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IMovieRepository extends CrudRepository<Movie, Long> {

	// Search by params
	@Query("SELECT m FROM Movie m WHERE m.title LIKE %?1% OR m.description LIKE %?1% OR m.popularity LIKE %?1%")
	public List<Movie> searchByParam(String param);

	// Filter by range rental price
	@Query("SELECT m FROM Movie m WHERE m.rentalPrice BETWEEN ?1 AND ?2")
	public List<Movie> filterByRangeRentalPrice(BigDecimal minPrice, BigDecimal maxPrice);

	// Filter by range purchase price
	@Query("SELECT m FROM Movie m WHERE m.purchasePrice BETWEEN ?1 AND ?2")
	public List<Movie> filterByRangePurchasePrice(BigDecimal minPrice, BigDecimal maxPrice);

	@Query(value = "{CALL SP_get_movies_by_genre()}", nativeQuery = true) // use Stored Procedure
	public List<Movie> filterByGenre(String genre);
}