package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.stock.StockDTO;
import com.estech.EstechAppBackend.service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/by-room/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StockDTO>> getStocksByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(stockService.getStocksByRoomId(roomId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
        StockDTO created = stockService.createStock(stockDTO);
        return ResponseEntity.created(URI.create("/api/stock/" + stockDTO.getId())).body(created);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockDTO> updateStock(@Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockService.updateStock(stockDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockDTO> modifyStock(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockService.updateStock(stockDTO));
    }

}
