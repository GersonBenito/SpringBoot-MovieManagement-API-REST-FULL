package com.movie.management.persistence.impl;

import com.movie.management.entities.Stock;
import com.movie.management.persistence.IStockDAO;
import com.movie.management.repositories.IStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StockDAOImpl implements IStockDAO {

    @Autowired
    private IStockRepository stockRepository;

    @Override
    public List<Stock> findAll() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        stockRepository.deleteById(id);
    }
}
