package com.estech.EstechAppBackend.converter.checkIn;


import com.estech.EstechAppBackend.dto.checkin.CheckInDto;
import com.estech.EstechAppBackend.model.CheckIn;
import org.springframework.stereotype.Component;

@Component
public class CheckInConverter {

    public CheckInDto convertCheckInToCheckInDTO(CheckIn checkIn) {
        CheckInDto checkInDto = new CheckInDto();

        checkInDto.setId(checkIn.getId());
        checkInDto.setDate(checkIn.getDate());
        checkInDto.setCheckIn(checkIn.getCheckIn());
        checkInDto.setUser(checkIn.getUser().getName() + " " + checkIn.getUser().getLastname());
        checkInDto.setUserId(checkIn.getUser().getId());

        return checkInDto;
    }

}
