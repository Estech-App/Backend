package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.room.RoomConverter;
import com.estech.EstechAppBackend.converter.room.RoomTimeTableConverter;
import com.estech.EstechAppBackend.dto.room.RoomDTO;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private RoomTimeTableConverter roomTimeTableConverter;

    @Autowired
    private RoomTimeTableRepository roomTimeTableRepository;

    public List<RoomDTO> getAllRoomDTOs() {
        return roomConverter.toRoomDtos(roomRepository.findAll());
    }

    public RoomDTO getRoomById(Long id) {
        Room room =  roomRepository.findById(id)
                .orElseThrow(() -> new AppException("Room with id " + id + " not found", HttpStatus.NOT_FOUND));

        return roomConverter.toRoomDto(room);
    }

    public RoomDTO createNewRoom(RoomDTO roomDTO) {
        Room room = Room.builder()
                .name(roomDTO.getName())
                .description(roomDTO.getDescription())
                .mentoringRoom(roomDTO.getMentoringRoom())
                .studyRoom(roomDTO.getStudyRoom())
                .build();

        Room newCreatedRoom = roomRepository.save(room);

        checkNonExistingRoomTimeTables(roomDTO.getTimeTables(), newCreatedRoom);

        Room saved = roomRepository.save(newCreatedRoom);

        return roomConverter.toRoomDto(saved);
    }

    public RoomDTO updateRoom(RoomDTO roomDTO) {
        if (roomDTO.getId() == null) {
            throw new AppException("Room id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        Room room = roomRepository.findById(roomDTO.getId())
                .orElseThrow(() -> new AppException("Room with id " + roomDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        checkNonExistingRoomTimeTables(roomDTO.getTimeTables(), room);

        roomConverter.updateRoom(room, roomConverter.toRoom(roomDTO));

        Room saved = roomRepository.save(room);

        return roomConverter.toRoomDto(saved);
    }

    public RoomDTO modifyRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException("Room with id " + id + " not found", HttpStatus.NOT_FOUND));

        if (roomDTO.getName() != null) {
            room.setName(roomDTO.getName());
        }
        if (roomDTO.getDescription() != null) {
            room.setDescription(roomDTO.getDescription());
        }
        if (roomDTO.getMentoringRoom() != null) {
            room.setMentoringRoom(roomDTO.getMentoringRoom());
        }
        if (roomDTO.getStudyRoom() != null) {
            room.setStudyRoom(roomDTO.getStudyRoom());
        }
        if (roomDTO.getTimeTables() != null) {
            room.setRoomTimeTables(roomTimeTableConverter.toRoomTimeTables(roomDTO.getTimeTables()));
        }

        Room saved = roomRepository.save(room);

        return roomConverter.toRoomDto(saved);
    }

    /**
     * Checks if the RoomTimeTable already exists.
     * If it does not exist, it creates it and links the received room to it.
     * Otherwise, it updates the RoomTimeTable info with the received Room info.
     * @param roomTimeTableDTOS List of RoomTimeTableDTO to be checked.
     * @param room Room that will be linked to the newly generated RoomTimeTables.
     */
    private void checkNonExistingRoomTimeTables(List<RoomTimeTableDTO> roomTimeTableDTOS, Room room) {
        List<RoomTimeTable> roomTimeTables = new ArrayList<>();

        roomTimeTableDTOS.forEach(roomTimeTableDTO -> {
            if (roomTimeTableDTO.getId() == null) {
                RoomTimeTable newRoomTimeTable = RoomTimeTable.builder()
                        .start(roomTimeTableDTO.getStart())
                        .end(roomTimeTableDTO.getEnd())
                        .room(room)
                        .status(RoomStatusEnum.valueOf(roomTimeTableDTO.getStatus()))
                        .build();

                roomTimeTableRepository.save(newRoomTimeTable);
                roomTimeTables.add(newRoomTimeTable);
            } else {
                RoomTimeTable roomTimeTable = roomTimeTableRepository.findById(roomTimeTableDTO.getId())
                        .orElseThrow(() -> new AppException("Room Time Table with id " + roomTimeTableDTO.getId() + " not found", HttpStatus.NOT_FOUND));

                roomTimeTableConverter.updateRoomTimeTable(roomTimeTable, roomTimeTableConverter.toRoomTimeTable(roomTimeTableDTO));

                roomTimeTableRepository.save(roomTimeTable);
            }
        });
        if (!roomTimeTables.isEmpty()) {
            room.setRoomTimeTables(roomTimeTables);
        }
    }

}
