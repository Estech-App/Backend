package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.converter.room.RoomConverter;
import com.estech.EstechAppBackend.dto.stock.StockDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.Stock;
import com.estech.EstechAppBackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockConverter {

    @Autowired
    private RoomConverter roomConverter;
    @Autowired
    private RoomRepository roomRepository;

    public StockDTO toStockDto(Stock stock) {
        return StockDTO.builder()
                .id(stock.getId())
                .name(stock.getName())
                .description(stock.getDescription())
                .quantity(stock.getQuantity())
                .room(roomConverter.toRoomDto(stock.getRoom()))
                .build();
    }

    public Stock toStock(StockDTO stockDTO) {
        Stock stock = new Stock();

        if (stockDTO.getId() != null) {
            stock.setId(stockDTO.getId());
        }
        stock.setName(stockDTO.getName());
        stock.setDescription(stockDTO.getDescription());
        stock.setQuantity(stockDTO.getQuantity());

        Room room = roomRepository.findById(stockDTO.getRoom().getId())
                .orElseThrow(() -> new AppException("Room with id " + stockDTO.getRoom().getId() + " not found", HttpStatus.NOT_FOUND));
        stock.setRoom(room);

        return stock;
    }

    public List<StockDTO> toStockDtos(List<Stock> stocks) {
        List<StockDTO> stockDTOS = new ArrayList<>();

        stocks.forEach(stock -> stockDTOS.add(toStockDto(stock)));
        return stockDTOS;
    }

    public List<Stock> toStocks(List<StockDTO> stockDTOS) {
        List<Stock> stocks = new ArrayList<>();

        stockDTOS.forEach(stockDTO -> stocks.add(toStock(stockDTO)));
        return stocks;
    }

    public void updateStock(Stock target, Stock source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setQuantity(source.getQuantity());
        target.setRoom(source.getRoom());
    }

}
