package com.movie.management.controllers.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StockDTO {
    private Long id;
    private Long amount;
}
