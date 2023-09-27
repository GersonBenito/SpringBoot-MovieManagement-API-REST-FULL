package com.movie.management.entities;

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
    private String image;
    private double popularity;
    @Column(name = "rental_price")
    private double rentalPrice;
    @Column(name = "purchase_price")
    private double purchasePrice;
    private boolean availability = true;

    @OneToOne(targetEntity = Stock.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_stock")
    private Stock stock;

    @OneToMany(targetEntity = Genre.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Genre> genres;
}
