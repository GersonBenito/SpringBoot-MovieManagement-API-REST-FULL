package com.movie.management.controller.DTO;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Genre;
import com.movie.management.entity.Movie;
import com.movie.management.entity.Stock;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO {

	public MovieDTO(Movie movie) {
		this(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getPosterImage(),
				movie.getBackgroundImage(), movie.getPopularity(), movie.getRentalPrice(), movie.getPurchasePrice(),
				movie.isAvailability(), movie.getStock(), movie.getGenres());
	}
	
	public static MovieDTO MovieDTOWithoutId(Movie movie) {
		MovieDTO dto = new MovieDTO(movie);
		dto.setId(null);
		return dto;
	}
	
	// this acts like an updater as well as a reverse constructor
	public static Movie Movie(MovieDTO movieDTO, Movie movie) {
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setPosterImage(movieDTO.getPosterImage());
        movie.setBackgroundImage(movieDTO.getBackgroundImage());
        movie.setPopularity(movieDTO.getPopularity());
        movie.setRentalPrice(movieDTO.getRentalPrice());
        movie.setPurchasePrice(movieDTO.getPurchasePrice());
        movie.setAvailability(movieDTO.isAvailability());
        movie.setStock(movieDTO.getStock());
        movie.setGenres(movieDTO.getGenres());
		return movie;
	}
	
	public static Optional<MovieDTO> MovieDTOOptional(Optional<Movie> movie) {
		return movie.map(MovieDTO::new);
	}

	private Long id;
	
	private String title;
	
	@NotNull
	private String description;
	
	private String posterImage;
	
	private String backgroundImage;
	
	@NotNull
	private double popularity;
	
	@NotNull
	@Column(name = "rental_price")
	private double rentalPrice;
	
	@Column(name = "purchase_price")
	@NotNull
	private double purchasePrice;
	
	@NotNull
	@Builder.Default
	private boolean availability = true;
	
	private Stock stock;
	
	private List<Genre> genres;
}
