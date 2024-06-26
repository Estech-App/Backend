package com.estech.EstechAppBackend.converter;

import com.estech.EstechAppBackend.converter.room.RoomConverter;
import com.estech.EstechAppBackend.dto.freeUsages.FreeUsagesDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.FreeUsages;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import com.estech.EstechAppBackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FreeUsageConverter {

    @Autowired
    private StatusService statusService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomConverter roomConverter;

    public FreeUsagesDTO toFreeUsagesDto(FreeUsages freeUsages) {
        return FreeUsagesDTO.builder()
                .id(freeUsages.getId())
                .start(freeUsages.getStart())
                .end(freeUsages.getEnd())
                .status(freeUsages.getStatus().getStatus().toString())
                .room(roomConverter.toRoomDto(freeUsages.getRoom()))
                .user(userConverter.convertUserEntityToUserInfoDTO(freeUsages.getUser()))
                .build();
    }

    public FreeUsages toFreeUsages(FreeUsagesDTO freeUsagesDTO) {
        FreeUsages freeUsages = new FreeUsages();

        if (freeUsagesDTO.getId() != null) {
            freeUsages.setId(freeUsagesDTO.getId());
        }
        freeUsages.setStart(freeUsagesDTO.getStart());
        freeUsages.setEnd(freeUsagesDTO.getEnd());

        if (freeUsagesDTO.getRoom() != null) {
            Room room = roomRepository.findById(freeUsagesDTO.getRoom().getId())
                    .orElseThrow(() -> new AppException("Room with id " + freeUsagesDTO.getRoom().getId() + " not found", HttpStatus.NOT_FOUND));
            freeUsages.setRoom(room);
        }

        statusService.getAllStatusEntities().forEach(status -> {
            if (status.getStatus().toString().equals(freeUsagesDTO.getStatus())) {
                freeUsages.setStatus(status);
            }
        });

        UserEntity user = userRepository.findById(freeUsagesDTO.getUser().getId())
                .orElseThrow(() -> new AppException("User with id " + freeUsagesDTO.getUser().getId() + " not found", HttpStatus.NOT_FOUND));
        freeUsages.setUser(user);

        return freeUsages;
    }

    public List<FreeUsagesDTO> toFreeUsagesDtos(List<FreeUsages> freeUsages) {
        List<FreeUsagesDTO> dtos = new ArrayList<>();

        freeUsages.forEach(freeUsage -> {
            dtos.add(this.toFreeUsagesDto(freeUsage));
        });
        return dtos;
    }

    public void updateFreeUsages(FreeUsages target, FreeUsages source) {
        if (source == null) {
            return ;
        }

        target.setId(source.getId());
        target.setStart(source.getStart());
        target.setEnd(source.getEnd());
        target.setStatus(source.getStatus());
        target.setRoom(source.getRoom());
        target.setUser(source.getUser());
    }

}
