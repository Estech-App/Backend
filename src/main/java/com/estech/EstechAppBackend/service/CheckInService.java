package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.checkIn.CheckInConverter;
import com.estech.EstechAppBackend.dto.checkin.CheckInDto;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.CheckIn;
import com.estech.EstechAppBackend.model.UserEntity;
import com.estech.EstechAppBackend.repository.CheckInRepository;
import com.estech.EstechAppBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private CheckInConverter checkInConverter;
    @Autowired
    private UserRepository userRepository;

    public CheckInDto createOrUpdateCheckIn(CheckIn checkIn) {
        return checkInConverter.convertCheckInToCheckInDTO(checkInRepository.save(checkIn));
    }

    public List<CheckInDto> getCheckinsByUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with id " + userId + " not found", HttpStatus.NOT_FOUND));

        return checkInConverter.toCheckinDtos(checkInRepository.findCheckInByUser(user));
    }

    public List<CheckInDto> getAllCheckIns() {
        List<CheckInDto> list = new ArrayList<>();
        checkInRepository.findAll().forEach(checkIn -> {
            list.add(checkInConverter.convertCheckInToCheckInDTO(checkIn));
        });
        return list;
    }

}
