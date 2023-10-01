package com.movie.management.repositories;

import com.movie.management.entities.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockRepository extends CrudRepository<Stock, Long> {

}
