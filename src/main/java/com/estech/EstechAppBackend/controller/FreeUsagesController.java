package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.freeUsages.FreeUsagesDTO;
import com.estech.EstechAppBackend.service.FreeUsagesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/free-usage")
public class FreeUsagesController {

    @Autowired
    private FreeUsagesService freeUsagesService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FreeUsagesDTO>> getAllFreeUsages() {
        return ResponseEntity.ok(freeUsagesService.getAllFreeUsages());
    }

    @GetMapping("/by-room/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FreeUsagesDTO>> getFreeUsagesByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(freeUsagesService.getFreeUsagesByRoom(roomId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('STUDENT')")
    public ResponseEntity<FreeUsagesDTO> createNewFreeUsage(@Valid @RequestBody FreeUsagesDTO freeUsagesDTO) {
        FreeUsagesDTO created = freeUsagesService.createFreeUsages(freeUsagesDTO);
        return ResponseEntity.created(URI.create("/api/free-usage/" + freeUsagesDTO.getId())).body(created);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('STUDENT')")
    public ResponseEntity<FreeUsagesDTO> updateFreeUsage(@Valid @RequestBody FreeUsagesDTO freeUsagesDTO) {
        return ResponseEntity.ok(freeUsagesService.updateFreeUsage(freeUsagesDTO));
    }

}
