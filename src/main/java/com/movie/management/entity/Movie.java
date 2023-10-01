package com.movie.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
	private double rentalPrice;

	@Column(name = "purchase_price")
	private double purchasePrice;

	@Builder.Default
	private boolean availability = true;

	@OneToOne(targetEntity = Stock.class, cascade = CascadeType.MERGE) // CascadeType.MERGE actualizara el registro asociado de la tabla stock
	@JoinColumn(name = "id_stock")
	private Stock stock;

	@ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
	@JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "id_movie"), inverseJoinColumns = @JoinColumn(name = "id_genre"))
	@JsonIgnore
	private List<Genre> genres;
}
