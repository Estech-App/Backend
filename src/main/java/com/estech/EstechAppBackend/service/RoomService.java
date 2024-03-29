package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.RoomConverter;
import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomConverter roomConverter;

    public RoomDTO createNewRoom(Room room) {
        return roomConverter.convertRoomtoRoomDTO(roomRepository.save(room));
    }

    public List<RoomDTO> getAllRoomDTOs() {
        List<RoomDTO> list = new ArrayList<>();
        roomRepository.findAll().forEach(room -> {
            list.add(roomConverter.convertRoomtoRoomDTO(room));
        });
        return list;
    }

}
