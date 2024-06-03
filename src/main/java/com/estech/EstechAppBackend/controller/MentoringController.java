package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.mentoring.MentoringDTO;
import com.estech.EstechAppBackend.service.MentoringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mentoring")
@CrossOrigin
public class MentoringController {

    @Autowired
    private MentoringService mentoringService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MentoringDTO>> getAllMentorings() {
        return ResponseEntity.ok(mentoringService.getAllMentorings());
    }

    @GetMapping("/by-teacher/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER')")
    public ResponseEntity<List<MentoringDTO>> getMentoringsByTeacherId(@PathVariable Long id) {
        return ResponseEntity.ok(mentoringService.getMentoringsByUserId(id, true));
    }

    @GetMapping("/by-student/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('STUDENT')")
    public ResponseEntity<List<MentoringDTO>> getMentoringsByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok(mentoringService.getMentoringsByUserId(id, false));
    }

    @GetMapping("/by-room/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MentoringDTO>> getMentoringsByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(mentoringService.getMentoringsByRoomId(roomId));
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<MentoringDTO> createMentoring(@Valid @RequestBody MentoringDTO mentoringDTO) {
        MentoringDTO createdMentoring = mentoringService.createMentoring(mentoringDTO);
        return ResponseEntity.created(URI.create("/api/mentoring/" + mentoringDTO.getId())).body(createdMentoring);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<MentoringDTO> updateMentoring(@Valid @RequestBody MentoringDTO mentoringDTO) {
        return ResponseEntity.ok(mentoringService.updateMentoring(mentoringDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<MentoringDTO> patchMentoring(@PathVariable Long id, @RequestBody MentoringDTO mentoringDTO) {
        return ResponseEntity.ok(mentoringService.modifyMentoring(id, mentoringDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MentoringDTO> deleteMentoring(@PathVariable Long id) {
        return ResponseEntity.ok(mentoringService.deleteMentoring(id));
    }

}
