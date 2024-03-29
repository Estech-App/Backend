package com.estech.EstechAppBackend.converter.checkIn;


import com.estech.EstechAppBackend.dto.checkin.CheckInDto;
import com.estech.EstechAppBackend.model.CheckIn;
import org.springframework.stereotype.Component;

@Component
public class CheckInConverter {

    public CheckInDto convertCheckInToCheckInDTO(CheckIn checkIn) {
        CheckInDto checkInDto = new CheckInDto();

        checkInDto.setDate(checkIn.getDate());
        checkInDto.setCheckIn(checkIn.getCheckIn());
        checkInDto.setUser(checkIn.getUser().getName() + " " + checkIn.getUser().getLastname());

        return checkInDto;
    }

}
