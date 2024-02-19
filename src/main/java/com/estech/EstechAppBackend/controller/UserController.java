package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.user.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.user.CreationUserDTO;
import com.estech.EstechAppBackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<CreatedUserDTO> getAllUsers() {
        return userService.getAllUsersAsCreatedUserDTO();
    }

    @PostMapping("/new-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody CreationUserDTO creationUserDTO) {
        return new ResponseEntity<>(userService.createNewUser(creationUserDTO), HttpStatus.CREATED);
    }
}
