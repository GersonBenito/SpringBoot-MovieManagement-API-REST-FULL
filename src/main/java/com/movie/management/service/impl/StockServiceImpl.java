package com.movie.management.services.impl;

import com.movie.management.entities.Stock;
import com.movie.management.persistence.IStockDAO;
import com.movie.management.services.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    private IStockDAO stockDAO;

    @Override
    public List<Stock> findAll() {
        return stockDAO.findAll();
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return stockDAO.findById(id);
    }

    @Override
    public void save(Stock stock) {
        stockDAO.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        stockDAO.deleteById(id);
    }
}
