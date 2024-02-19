package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.stock.StockDTO;
import com.estech.EstechAppBackend.model.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockConverter {

    public StockDTO convertStockToStockDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();

        stockDTO.setName(stock.getName());
        stockDTO.setDescription(stock.getDescription());
        stockDTO.setQuantity(stock.getQuantity());
        stockDTO.setRoomName(stock.getRoom().getName());

        return stockDTO;
    }

}
