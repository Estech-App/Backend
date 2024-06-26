package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.FreeUsageConverter;
import com.estech.EstechAppBackend.dto.freeUsages.FreeUsagesDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.*;
import com.estech.EstechAppBackend.model.enums.RoomStatusEnum;
import com.estech.EstechAppBackend.repository.FreeUsagesRepository;
import com.estech.EstechAppBackend.repository.RoomRepository;
import com.estech.EstechAppBackend.repository.RoomTimeTableRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeUsagesService {

    @Autowired
    private FreeUsagesRepository freeUsagesRepository;
    @Autowired
    private FreeUsageConverter freeUsageConverter;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTimeTableRepository roomTimeTableRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusService statusService;

    public List<FreeUsagesDTO> getAllFreeUsages() {
        return freeUsageConverter.toFreeUsagesDtos(freeUsagesRepository.findAll());
    }

    public List<FreeUsagesDTO> getFreeUsagesByRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException("Room with id " + roomId + " not found", HttpStatus.NOT_FOUND));

        return freeUsageConverter.toFreeUsagesDtos(freeUsagesRepository.findFreeUsagesByRoom(room));
    }

    public List<FreeUsagesDTO> getFreeUsagesByStudent(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User with id " + id + " not found", HttpStatus.NOT_FOUND));

        return freeUsageConverter.toFreeUsagesDtos(freeUsagesRepository.findFreeUsagesByUser(user));
    }

    public FreeUsagesDTO createFreeUsages(FreeUsagesDTO freeUsagesDTO) {
        FreeUsages freeUsages = freeUsageConverter.toFreeUsages(freeUsagesDTO);

        FreeUsages saved = freeUsagesRepository.save(freeUsages);

        RoomTimeTable roomTimeTable = RoomTimeTable.builder()
                .status(RoomStatusEnum.OCCUPIED)
                .room(freeUsages.getRoom())
                .start(freeUsages.getStart())
                .end(freeUsages.getEnd())
                .build();
        roomTimeTableRepository.save(roomTimeTable);

        return freeUsageConverter.toFreeUsagesDto(saved);
    }

    public FreeUsagesDTO updateFreeUsage(FreeUsagesDTO freeUsagesDTO) {
        if (freeUsagesDTO.getId() == null) {
            throw new AppException("FreeUsage id must be provided for updating", HttpStatus.BAD_REQUEST);
        }

        FreeUsages freeUsages = freeUsagesRepository.findById(freeUsagesDTO.getId())
                .orElseThrow(() -> new AppException("FreeUsage with id " + freeUsagesDTO.getId() + " not found", HttpStatus.NOT_FOUND));

        freeUsageConverter.updateFreeUsages(freeUsages, freeUsageConverter.toFreeUsages(freeUsagesDTO));

        FreeUsages saved = freeUsagesRepository.save(freeUsages);

        return freeUsageConverter.toFreeUsagesDto(saved);
    }

    public FreeUsagesDTO modifyFreeUsage(Long id, FreeUsagesDTO freeUsagesDTO) {
        FreeUsages freeUsages = freeUsagesRepository.findById(id)
                .orElseThrow(() -> new AppException("FreeUsage with id " + id + " not foudn", HttpStatus.NOT_FOUND));

        if (freeUsagesDTO.getStart() != null) {
            freeUsages.setStart(freeUsagesDTO.getStart());
        }
        if (freeUsagesDTO.getEnd() != null) {
            freeUsages.setEnd(freeUsagesDTO.getEnd());
        }
        if (freeUsagesDTO.getStatus() != null) {
            statusService.getAllStatusEntities().forEach(status -> {
                if (status.getStatus().toString().equals(freeUsagesDTO.getStatus())) {
                    freeUsages.setStatus(status);
                }
            });
        }
        if (freeUsagesDTO.getRoom() != null) {
            Room room = roomRepository.findById(freeUsagesDTO.getRoom().getId())
                    .orElseThrow(() -> new AppException("Room with id " + freeUsagesDTO.getRoom().getId() + " not found", HttpStatus.NOT_FOUND));
            freeUsages.setRoom(room);
        }

        freeUsagesRepository.save(freeUsages);

        return freeUsageConverter.toFreeUsagesDto(freeUsages);
    }

    public FreeUsagesDTO deleteFreeUsage(Long id) {
        FreeUsages freeUsages = freeUsagesRepository.findById(id)
                .orElseThrow(() -> new AppException("FreeUsage with id " + " not found", HttpStatus.NOT_FOUND));

        freeUsagesRepository.deleteById(id);

        return freeUsageConverter.toFreeUsagesDto(freeUsages);
    }

}
