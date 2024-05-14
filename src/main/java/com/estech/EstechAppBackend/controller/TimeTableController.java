package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.group.TimeTableDTO;
import com.estech.EstechAppBackend.service.TimeTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/time-table")
public class TimeTableController {

    @Autowired
    private TimeTableService timeTableService;

    @GetMapping
    public ResponseEntity<List<TimeTableDTO>> getAllTimeTables() {
        return ResponseEntity.ok(timeTableService.getAllTimeTables());
    }

    @GetMapping("/by-group-id/{id}")
    public ResponseEntity<List<TimeTableDTO>> getAllTimeTablesByGroupId(@PathVariable Long id) {
        return ResponseEntity.ok(timeTableService.getAllTimeTablesByGroupId(id));
    }

    @PostMapping
    public ResponseEntity<TimeTableDTO> createTimeTable(@Valid @RequestBody TimeTableDTO timeTableDTO) {
        TimeTableDTO created = timeTableService.createTimeTable(timeTableDTO);
        return ResponseEntity.created(URI.create("/api/time-table/" + timeTableDTO.getId())).body(created);
    }

    @PutMapping
    public ResponseEntity<TimeTableDTO> updateTimeTable(@Valid @RequestBody TimeTableDTO timeTableDTO) {
        return ResponseEntity.ok(timeTableService.updateTimeTable(timeTableDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TimeTableDTO> modifyTimeTable(@PathVariable Long id, @RequestBody TimeTableDTO timeTableDTO) {
        return ResponseEntity.ok(timeTableService.modifyTimeTable(id, timeTableDTO));
    }

}
