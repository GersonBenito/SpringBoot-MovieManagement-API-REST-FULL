package com.movie.management.services;

import com.movie.management.entities.Stock;

import java.util.List;
import java.util.Optional;

public interface IStockService {
    public List<Stock> findAll();
    public Optional<Stock> findById(Long id);
    public void save(Stock stock);
    public void deleteById(Long id);
}
