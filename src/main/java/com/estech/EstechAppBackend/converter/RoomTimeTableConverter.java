package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.room.RoomTimeTableDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.RoomTimeTable;
import com.estech.EstechAppBackend.model.enums.RoomStatusEnum;
import com.estech.EstechAppBackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomTimeTableConverter {

    @Autowired
    private RoomRepository roomRepository;

    public RoomTimeTableDTO toRoomTimeTableDTO(RoomTimeTable roomTimeTable) {
        return RoomTimeTableDTO.builder()
                .id(roomTimeTable.getId())
                .status(roomTimeTable.getStatus().toString())
                .roomId(roomTimeTable.getRoom().getId())
                .date(roomTimeTable.getDate())
                .build();
    }

    public RoomTimeTable toRoomTimeTable(RoomTimeTableDTO roomTimeTableDTO) {
        RoomTimeTable roomTimeTable = new RoomTimeTable();

        if (roomTimeTableDTO.getId() != null) {
            roomTimeTable.setId(roomTimeTableDTO.getId());
        }

        Room room = roomRepository.findById(roomTimeTableDTO.getRoomId())
                .orElseThrow(() -> new AppException("Room with id " + roomTimeTableDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
        roomTimeTable.setRoom(room);

        roomTimeTable.setDate(roomTimeTableDTO.getDate());

        roomTimeTable.setStatus(RoomStatusEnum.valueOf(roomTimeTableDTO.getStatus()));

        return roomTimeTable;
    }

    public List<RoomTimeTableDTO> toRoomTimeTableDtos(List<RoomTimeTable> roomTimeTables) {
        List<RoomTimeTableDTO> roomTimeTableDTOs = new ArrayList<>();

        roomTimeTables.forEach(roomTimeTable -> {
            roomTimeTableDTOs.add(this.toRoomTimeTableDTO(roomTimeTable));
        });
        return roomTimeTableDTOs;
    }

    public List<RoomTimeTable> toRoomTimeTables(List<RoomTimeTableDTO> roomTimeTableDTOS) {
        List<RoomTimeTable> roomTimeTables = new ArrayList<>();

        roomTimeTableDTOS.forEach(roomTimeTableDTO -> {
            roomTimeTables.add(this.toRoomTimeTable(roomTimeTableDTO));
        });
        return roomTimeTables;
    }

    public void updateRoomTimeTable(RoomTimeTable target, RoomTimeTable source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setDate(source.getDate());
        target.setStatus(source.getStatus());
        target.setRoom(source.getRoom());
    }

}
