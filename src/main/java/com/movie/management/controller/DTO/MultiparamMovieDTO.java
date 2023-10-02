package com.movie.management.controller.DTO;

import java.util.Set;

import com.movie.management.entity.Genre;
import com.movie.management.entity.Stock;

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

// DTO to verify multiparam search param names and data type. It should match with Movie Entity class for data type integrity.
// Also, this class holds the allowed param types for search
public class MultiparamMovieDTO {

	// private Long id;
	
	private String title;
	
	private String description;
	
	private Double popularity;
	
	private Double rentalPrice;
	
	private Double purchasePrice;

	private Boolean availability;
	
	private Stock stock;
	
	private Set<Genre> genres;
}
