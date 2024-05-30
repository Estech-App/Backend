package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.room.RoomTimeTableConverter;
import com.estech.EstechAppBackend.dto.room.RoomTimeTableDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.RoomTimeTable;
import com.estech.EstechAppBackend.model.enums.RoomStatusEnum;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.RoomTimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTimeTableService {

    @Autowired
    private RoomTimeTableRepository roomTimeTableRepository;
    @Autowired
    private RoomTimeTableConverter roomTimeTableConverter;
    @Autowired
    private RoomRepository roomRepository;

    public List<RoomTimeTableDTO> getAllRoomTimeTables() {
        return roomTimeTableConverter.toRoomTimeTableDtos(roomTimeTableRepository.findAll());
    }

    public List<RoomTimeTableDTO> getAllRoomTimeTablesByRoomId(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException("Room with id " + id + " not found", HttpStatus.NOT_FOUND));

        return roomTimeTableConverter.toRoomTimeTableDtos(room.getRoomTimeTables());
    }

    public RoomTimeTableDTO createRoomTimeTable(RoomTimeTableDTO roomTimeTableDTO) {
        RoomTimeTable roomTimeTable = roomTimeTableConverter.toRoomTimeTable(roomTimeTableDTO);

        RoomTimeTable saved = roomTimeTableRepository.save(roomTimeTable);

        return roomTimeTableConverter.toRoomTimeTableDTO(saved);
    }

    public RoomTimeTableDTO updateRoomTimeTable(RoomTimeTableDTO roomTimeTableDTO) {
        if (roomTimeTableDTO.getId() == null) {
            throw new AppException("Room Time Table id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        RoomTimeTable roomTimeTable = roomTimeTableRepository.findById(roomTimeTableDTO.getId())
                .orElseThrow(() -> new AppException("Room Time table with id " + roomTimeTableDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        roomTimeTableConverter.updateRoomTimeTable(roomTimeTable, roomTimeTableConverter.toRoomTimeTable(roomTimeTableDTO));

        RoomTimeTable saved = roomTimeTableRepository.save(roomTimeTable);

        return roomTimeTableConverter.toRoomTimeTableDTO(saved);
    }

    public RoomTimeTableDTO modifyRoomTimeTable(Long id, RoomTimeTableDTO roomTimeTableDTO) {
        RoomTimeTable roomTimeTable = roomTimeTableRepository.findById(id)
                .orElseThrow(() -> new AppException("Room Time Table with id " + id + " not found", HttpStatus.NOT_FOUND));

        if (roomTimeTableDTO.getRoomId() != null) {
            Room room = roomRepository.findById(roomTimeTableDTO.getRoomId())
                    .orElseThrow(() -> new AppException("Room with id " + roomTimeTableDTO.getRoomId() + " not found", HttpStatus.NOT_FOUND));
            roomTimeTable.setRoom(room);
        }
        if (roomTimeTableDTO.getStatus() != null) {
            roomTimeTable.setStatus(RoomStatusEnum.valueOf(roomTimeTableDTO.getStatus()));
        }
        if (roomTimeTableDTO.getStart() != null) {
            roomTimeTable.setStart(roomTimeTableDTO.getStart());
        }
        if (roomTimeTableDTO.getReccurence() != null) {
            roomTimeTable.setReccurence(roomTimeTableDTO.getReccurence());
        }
        if (roomTimeTableDTO.getEnd() != null) {
            roomTimeTable.setEnd(roomTimeTableDTO.getEnd());
        }
        if (roomTimeTableDTO.getDayOfWeek() != null) {
            roomTimeTable.setDayOfWeek(roomTimeTableDTO.getDayOfWeek());
        }

        RoomTimeTable saved = roomTimeTableRepository.save(roomTimeTable);

        return roomTimeTableConverter.toRoomTimeTableDTO(saved);
    }

}
