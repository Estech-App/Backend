package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.dto.room.RoomIdDTO;
import com.estech.EstechAppBackend.model.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoomConverter {

    public RoomDTO convertRoomtoRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        Map<String, String> map = new HashMap<>();

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

    public RoomIdDTO convertRoomToRoomIdDTO(Room room) {
        return new RoomIdDTO(room.getId());
    }

}
