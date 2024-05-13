package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.room.RoomTimeTableDTO;
import com.estech.EstechAppBackend.service.RoomTimeTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/room-time-table")
public class RoomTimeTableController {

    @Autowired
    private RoomTimeTableService roomTimeTableService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoomTimeTableDTO>> getAllRoomTimeTables() {
        return ResponseEntity.ok(roomTimeTableService.getAllRoomTimeTables());
    }

    @GetMapping("/by-room-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoomTimeTableDTO>> getAllRoomTimeTablesByRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(roomTimeTableService.getAllRoomTimeTablesByRoomId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomTimeTableDTO> createRoomTimeTable(@Valid @RequestBody RoomTimeTableDTO roomTimeTableDTO) {
        RoomTimeTableDTO created = roomTimeTableService.createRoomTimeTable(roomTimeTableDTO);
        return ResponseEntity.created(URI.create("/room-time-table/" + roomTimeTableDTO.getId())).body(created);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomTimeTableDTO> updateRoomTimeTable(@Valid @RequestBody RoomTimeTableDTO roomTimeTableDTO) {
        return ResponseEntity.ok(roomTimeTableService.updateRoomTimeTable(roomTimeTableDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomTimeTableDTO> modifyRoomTimeTable(@PathVariable Long id, @RequestBody RoomTimeTableDTO roomTimeTableDTO) {
        return ResponseEntity.ok(roomTimeTableService.modifyRoomTimeTable(id, roomTimeTableDTO));
    }

}
