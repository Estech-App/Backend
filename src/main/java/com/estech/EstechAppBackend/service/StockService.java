package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.StockConverter;
import com.estech.EstechAppBackend.dto.stock.StockDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.Stock;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockConverter stockConverter;
    @Autowired
    private RoomRepository roomRepository;

    public List<StockDTO> getAllStocks(){
        return stockConverter.toStockDtos(stockRepository.findAll());
    }

    public List<StockDTO> getStocksByRoomId(Long roomId){
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException("Room with id " + roomId + " not found", HttpStatus.NOT_FOUND));

        return stockConverter.toStockDtos(stockRepository.findStockByRoom(room));
    }

    public StockDTO createStock(StockDTO stockDTO) {
        Stock saved = stockRepository.save(stockConverter.toStock(stockDTO));

        return stockConverter.toStockDto(saved);
    }

    public StockDTO updateStock(StockDTO stockDTO) {
        if (stockDTO.getId() == null) {
            throw new AppException("Stock id must be provided for updating ", HttpStatus.BAD_REQUEST);
        }

        Stock stock = stockRepository.findById(stockDTO.getId())
                .orElseThrow(() -> new AppException("Stock with id " + stockDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        stockConverter.updateStock(stock, stockConverter.toStock(stockDTO));

        Stock saved = stockRepository.save(stock);

        return stockConverter.toStockDto(saved);
    }

    public StockDTO modifyStock(Long id, StockDTO stockDTO) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new AppException("Stock with id " + id + " not found", HttpStatus.NOT_FOUND));

        if (stockDTO.getName() != null) {
            stock.setName(stockDTO.getName());
        }
        if (stockDTO.getDescription() != null) {
            stock.setDescription(stockDTO.getDescription());
        }
        if (stockDTO.getQuantity() != null) {
            stock.setQuantity(stockDTO.getQuantity());
        }
        if (stockDTO.getRoom() != null) {
            Room room = roomRepository.findById(stockDTO.getRoom().getId())
                    .orElseThrow(() -> new AppException("Room with id " + stockDTO.getRoom().getId() + " not found", HttpStatus.NOT_FOUND));

            stock.setRoom(room);
        }

        Stock saved = stockRepository.save(stock);

        return stockConverter.toStockDto(saved);
    }

}
