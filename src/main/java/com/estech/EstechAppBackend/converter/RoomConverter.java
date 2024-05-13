package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.dto.idDTO;
import com.estech.EstechAppBackend.model.Room;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomConverter {

    public RoomDTO convertRoomtoRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        Map<String, String> map = new HashMap<>();

        roomDTO.setId(roomDTO.getId());
        roomDTO.setName(room.getName());
        roomDTO.setDescription(room.getDescription());
        roomDTO.setStudyRoom(room.getStudyRoom());
        roomDTO.setMentoringRoom(room.getMentoringRoom());
        if(room.getRoomTimeTables() != null) {
            room.getRoomTimeTables().forEach(timetable -> {
                map.put(timetable.getDate().toString(), timetable.getStatus().toString());
            });
            roomDTO.setTimeTableStatus(map);
        }

        return roomDTO;
    }

    public Room convertRoomDTOToRoom(RoomDTO roomDTO) {
        Room room = new Room();

        if (roomDTO.getId() != null) {
            room.setId(roomDTO.getId());
        }
        room.setName(roomDTO.getName());
        room.setDescription(roomDTO.getDescription());
        room.setStudyRoom(roomDTO.getStudyRoom());
        room.setMentoringRoom(roomDTO.getMentoringRoom());
        // TODO - TimeTables
        return room;
    }

    public List<RoomDTO> convertRoomsToRoomDTOs(List<Room> rooms) {
        List<RoomDTO> roomDTOs = new ArrayList<>();

        rooms.forEach(room -> {
            roomDTOs.add(this.convertRoomtoRoomDTO(room));
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
