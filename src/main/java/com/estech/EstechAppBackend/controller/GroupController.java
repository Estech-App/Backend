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
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllGroups() {
        if (groupService.getAllGroups().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewGroup(@Valid @RequestBody Group group) {
        return new ResponseEntity<>(groupService.createNewGroup(group), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/add-room/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARY')")
    public ResponseEntity<?> addRoomToGroup(@PathVariable Long id, @RequestBody idDTO roomId) {
        GroupDTO groupDTO = groupService.addRoomToGroup(id, roomId);

        if (groupDTO == null) {
            return new ResponseEntity<>("Non found entity", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(groupDTO, HttpStatus.ACCEPTED);
    }
}
