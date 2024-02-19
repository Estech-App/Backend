package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomConverter {

    public RoomDTO convertRoomtoRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setName(room.getName());
        roomDTO.setDescription(room.getDescription());
        roomDTO.setStudyRoom(room.getStudyRoom());
        roomDTO.setMentoringRoom(room.getMentoringRoom());

        return roomDTO;
    }

}
