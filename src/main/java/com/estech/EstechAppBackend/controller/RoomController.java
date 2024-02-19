package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.room.RoomDTO;
import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY')")
    public ResponseEntity<?> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRoomDTOs(), HttpStatus.OK);
    }

    @PostMapping("/new-room")
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY')")
    public ResponseEntity<?> createNewRoom(@Valid @RequestBody Room room) {
        return new ResponseEntity<>(roomService.createNewRoom(room), HttpStatus.CREATED);
    }

}
