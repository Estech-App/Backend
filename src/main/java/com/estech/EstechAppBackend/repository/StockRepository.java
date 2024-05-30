package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findStockByRoom(Room room);
}