package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.checkIn.CheckInConverter;
import com.estech.EstechAppBackend.dto.checkin.CheckInDto;
import com.estech.EstechAppBackend.model.CheckIn;
import com.estech.EstechAppBackend.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CheckInConverter checkInConverter;

    public CheckInDto createNewCheckIn(CheckIn checkIn) {

        CheckIn lastCheckIn = checkInRepository.getCheckInOrderDesc(checkIn.getUser().getId()).get(0);

        if(lastCheckIn.getCheckIn() && checkIn.getCheckIn()) {
            return null;
        } else if(!lastCheckIn.getCheckIn() && !checkIn.getCheckIn()) {
            return null;
        }

        return checkInConverter.convertCheckInToCheckInDTO(checkInRepository.save(checkIn));
    }

    public List<CheckInDto> getAllCheckIns() {
        List<CheckInDto> list = new ArrayList<>();
        checkInRepository.findAll().forEach(checkIn -> {
            list.add(checkInConverter.convertCheckInToCheckInDTO(checkIn));
        });
        return list;
    }

}
