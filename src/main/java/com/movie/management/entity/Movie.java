package com.movie.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	@Column(name = "poster_image")
	private String posterImage;
	@Column(name = "background_image")
	private String backgroundImage;
	private double popularity;
	@Column(name = "rental_price")
	private BigDecimal rentalPrice; // el BigDecimal es mas preciso cuando se trabaja con monedas
	@Column(name = "purchase_price")
	private BigDecimal purchasePrice; // el BigDecimal es mas preciso cuando se trabaja con monedas
	private boolean availability = true;

	@OneToOne(targetEntity = Stock.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL) // CascadeType.MERGE actualizara el registro asociado de la tabla stock
	@JoinColumn(name = "id_stock")
	private Stock stock;

	//private int stock;
	@ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
	@JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "id_movie"), inverseJoinColumns = @JoinColumn(name = "id_genre"))
	@JsonIgnore
	private List<Genre> genres;
}