package com.movie.management.service;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Stock;

public interface IStockService {
    public List<Stock> findAll();
    public Optional<Stock> findById(Long id);
    public void save(Stock stock);
    public void deleteById(Long id);
}
