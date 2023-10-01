package com.movie.management.controllers.DTO;

import com.movie.management.entities.Genre;
import com.movie.management.entities.Stock;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    private double rentalPrice;
    @Column(name = "purchase_price")
    @NotNull
    private double purchasePrice;
    @NotNull
    private boolean availability = true;
    private Stock stock;
    private List<Genre> genres;
}
