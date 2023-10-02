package com.movie.management.entity;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;

	private String description;

	private String posterImage;

	private String backgroundImage;

	private double popularity;

	private double rentalPrice;

	private double purchasePrice;

	@Builder.Default
	private boolean availability = true;

	@OneToOne(targetEntity = Stock.class, cascade = CascadeType.MERGE) // CascadeType.MERGE actualizara el registro asociado de la tabla stock
	@JoinColumn(name = "id_stock")
	private Stock stock;

	@ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
	@JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "id_movie"), inverseJoinColumns = @JoinColumn(name = "id_genre"))
	@JsonIgnore
	private Set<Genre> genres;
	
	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;

		return Objects.equals(id, other.id) && (title!=null && title.equalsIgnoreCase(other.title));
	}
}
