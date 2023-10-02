package com.movie.management.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long amount;
	
	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Stock)) {
			return false;
		}
		Stock other = (Stock) obj;
		return Objects.equals(amount, other.amount);
	}
}
