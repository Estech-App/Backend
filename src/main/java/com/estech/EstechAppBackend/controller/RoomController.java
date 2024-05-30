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

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<?> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRoomDTOs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<RoomDTO> getRoomByRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping("/new-room")
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY')")
    public ResponseEntity<RoomDTO> createNewRoom(@Valid @RequestBody RoomDTO roomDto) {
        RoomDTO created = roomService.createNewRoom(roomDto);
        return ResponseEntity.created(URI.create("/api/room/new-room/" + roomDto.getId())).body(created);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDTO> updateRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(roomDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDTO> modifyRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.modifyRoom(id, roomDTO));
    }

}
