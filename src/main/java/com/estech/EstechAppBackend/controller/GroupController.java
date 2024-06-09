package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.group.GroupDTO;
import com.estech.EstechAppBackend.dto.idDTO;
import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('TEACHER') || hasRole('STUDENT')")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDTO> createNewGroup(@Valid @RequestBody GroupDTO groupDTO) {
        GroupDTO created = groupService.createNewGroup(groupDTO);
        return ResponseEntity.created(URI.create("/api/groups/" + groupDTO.getId())).body(created);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.updateGroup(groupDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<GroupDTO> modifyGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.ok(groupService.modifyGroup(id, groupDTO));
    }
}
