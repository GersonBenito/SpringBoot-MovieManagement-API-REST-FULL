package com.movie.management.entity;

import com.movie.management.util.EType;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "acquisition_detail")
public class AcquisitionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EType type;

    @OneToOne(targetEntity = Movie.class)
    @JoinColumn(name = "id_movie")
    private Movie movie;
}
