package com.estech.EstechAppBackend.converter.room;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomConverter {

    @Autowired
    private RoomTimeTableConverter roomTimeTableConverter;

    public RoomDTO toRoomDto(Room room) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setDescription(room.getDescription());
        roomDTO.setStudyRoom(room.getStudyRoom());
        roomDTO.setMentoringRoom(room.getMentoringRoom());
        roomDTO.setTimeTables(roomTimeTableConverter.toRoomTimeTableDtos(room.getRoomTimeTables()));

        return roomDTO;
    }

    public Room toRoom(RoomDTO roomDTO) {
        Room room = new Room();

        if (roomDTO.getId() != null) {
            room.setId(roomDTO.getId());
        }
        room.setName(roomDTO.getName());
        room.setDescription(roomDTO.getDescription());
        room.setStudyRoom(roomDTO.getStudyRoom());
        room.setMentoringRoom(roomDTO.getMentoringRoom());
        room.setRoomTimeTables(roomTimeTableConverter.toRoomTimeTables(roomDTO.getTimeTables()));

        return room;
    }

    public List<RoomDTO> toRoomDtos(List<Room> rooms) {
        List<RoomDTO> roomDTOs = new ArrayList<>();

        rooms.forEach(room -> {
            roomDTOs.add(this.toRoomDto(room));
        });
        return roomDTOs;
    }

    public void updateRoom(Room target, Room source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setStudyRoom(source.getStudyRoom());
        target.setMentoringRoom(source.getMentoringRoom());
        target.setRoomTimeTables(source.getRoomTimeTables());
        target.setGroups(source.getGroups());
        target.setMentorings(source.getMentorings());
        target.setFreeUsages(source.getFreeUsages());
        target.setStocks(source.getStocks());
    }

}
