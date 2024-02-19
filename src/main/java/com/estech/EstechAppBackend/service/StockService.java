package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.StockConverter;
import com.estech.EstechAppBackend.dto.stock.StockDTO;
import com.estech.EstechAppBackend.model.Stock;
import com.estech.EstechAppBackend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockConverter stockConverter;

    public StockDTO createNewStock(Stock stock) {
        return stockConverter.convertStockToStockDTO(stockRepository.save(stock));
    }

    public List<StockDTO> getAllStock() {
        List<StockDTO> list = new ArrayList<>();
        stockRepository.findAll().forEach(stock -> {
            list.add(stockConverter.convertStockToStockDTO(stock));
        });
        return list;
    }

}
