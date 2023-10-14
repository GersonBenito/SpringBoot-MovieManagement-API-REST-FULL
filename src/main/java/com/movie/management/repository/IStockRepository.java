package com.movie.management.repository;

import com.movie.management.entity.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.management.entity.Stock;

@Repository
public interface IStockRepository extends CrudRepository<Stock, Long> {

}