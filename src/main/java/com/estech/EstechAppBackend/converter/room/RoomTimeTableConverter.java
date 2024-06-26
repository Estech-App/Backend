package com.estech.EstechAppBackend.converter.room;

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

        RoomStatusEnum roomStatusEnum = roomTimeTable.getStatus();
        Room room = roomTimeTable.getRoom();

        RoomTimeTableDTO dto = RoomTimeTableDTO.builder()
                .status(roomStatusEnum != null ? roomStatusEnum.toString() : "OCCUPIED")
                .roomId(room != null ? room.getId() : -1)
                .start(roomTimeTable.getStart())
                .end(roomTimeTable.getEnd())
                .dayOfWeek(roomTimeTable.getDayOfWeek())
                .reccurence(roomTimeTable.getReccurence())
                .build();

        Long id = roomTimeTable.getId();
        if (id != null) {
            dto.setId(id);
        }

        return dto;
    }

    public RoomTimeTable toRoomTimeTable(RoomTimeTableDTO roomTimeTableDTO) {
        RoomTimeTable roomTimeTable = new RoomTimeTable();

        if (roomTimeTableDTO.getId() != null) {
            roomTimeTable.setId(roomTimeTableDTO.getId());
        }

        Room room = roomRepository.findById(roomTimeTableDTO.getRoomId())
                .orElseThrow(() -> new AppException("Room with id " + roomTimeTableDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
        roomTimeTable.setRoom(room);

        roomTimeTable.setStart(roomTimeTableDTO.getStart());
        roomTimeTable.setEnd(roomTimeTableDTO.getEnd());
        roomTimeTable.setDayOfWeek(roomTimeTableDTO.getDayOfWeek());

        roomTimeTable.setStatus(RoomStatusEnum.valueOf(roomTimeTableDTO.getStatus()));
        roomTimeTable.setReccurence(roomTimeTableDTO.getReccurence());

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
        target.setStart(source.getStart());
        target.setEnd(source.getEnd());
        target.setDayOfWeek(source.getDayOfWeek());
        target.setReccurence(source.getReccurence());
        target.setStatus(source.getStatus());
        target.setRoom(source.getRoom());
    }

}
