package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.checkin.CheckInDto;
import com.estech.EstechAppBackend.model.CheckIn;
import com.estech.EstechAppBackend.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER')")
    public ResponseEntity<List<CheckInDto>> getCheckinsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(checkInService.getCheckinsByUser(userId));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY') || hasRole('TEACHER')")
    public ResponseEntity<?> createNewCheckIn(@Valid @RequestBody CheckIn checkIn) {
        return new ResponseEntity<>(checkInService.createOrUpdateCheckIn(checkIn), HttpStatus.CREATED);
    }

    @PutMapping("/update-checkin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCheckin(@Valid @RequestBody CheckIn checkIn) {
        return new ResponseEntity<>(checkInService.createOrUpdateCheckIn(checkIn), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CheckInDto> deleteCheckIn(@PathVariable Long id) {
        return ResponseEntity.ok(checkInService.deleteCheckin(id));
    }

}
