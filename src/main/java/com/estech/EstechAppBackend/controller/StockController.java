package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.stock.StockDTO;
import com.estech.EstechAppBackend.model.Stock;
import com.estech.EstechAppBackend.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY')")
    public ResponseEntity<?> getAllStock() {
        return new ResponseEntity<>(stockService.getAllStock(), HttpStatus.OK);
    }

    @PostMapping("/new-stock")
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY')")
    public ResponseEntity<?> createNewStock(@Valid @RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.createNewStock(stock), HttpStatus.CREATED);
    }

}
