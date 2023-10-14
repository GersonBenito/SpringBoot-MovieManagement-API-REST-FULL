package com.movie.management.controller.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.movie.management.entity.Genre;
import com.movie.management.entity.Stock;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO {
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
	@NotNull
	private BigDecimal rentalPrice;
	@Column(name = "purchase_price")
	@NotNull
	private BigDecimal purchasePrice;
	@NotNull
	private boolean availability = true;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Stock stock;
	private List<Genre> genres;
}