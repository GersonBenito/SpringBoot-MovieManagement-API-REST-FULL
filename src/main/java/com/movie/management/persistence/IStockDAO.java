package com.movie.management.persistence;

import com.movie.management.entity.Stock;

import java.util.List;
import java.util.Optional;

import com.movie.management.entity.Stock;

public interface IStockDAO {
    public List<Stock>findAll();
    public Optional<Stock>findById(Long id);
    public void save(Stock stock);
    public void deleteById(Long id);
}