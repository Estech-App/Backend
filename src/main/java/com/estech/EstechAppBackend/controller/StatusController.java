package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.StatusDTO;
import com.estech.EstechAppBackend.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<List<StatusDTO>> getAllStatus() {
        return ResponseEntity.ok(statusService.getAllStatus());
    }

}
