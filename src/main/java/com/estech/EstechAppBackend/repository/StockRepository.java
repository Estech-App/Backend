package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}