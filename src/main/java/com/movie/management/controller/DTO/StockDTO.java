package com.movie.management.controller.DTO;

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
