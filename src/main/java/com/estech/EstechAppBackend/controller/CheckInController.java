package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.checkin.CheckInDto;
import com.estech.EstechAppBackend.model.CheckIn;
import com.estech.EstechAppBackend.service.CheckInService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check-in")
@CrossOrigin
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCheckIns() {
        return new ResponseEntity<>(checkInService.getAllCheckIns(), HttpStatus.OK);
    }

    @PostMapping("/new")
   @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY') || hasRole('TEACHER')")
    public ResponseEntity<?> createNewCheckIn(@Valid @RequestBody CheckIn checkIn) {
        CheckInDto checkInDto = checkInService.createNewCheckIn(checkIn);

        if(checkInDto == null) {
            return new ResponseEntity<>("Non valid checkin", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(checkInService.createNewCheckIn(checkIn), HttpStatus.CREATED);
    }

}
