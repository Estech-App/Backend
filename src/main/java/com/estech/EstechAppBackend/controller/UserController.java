package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.user.CreatedUserDTO;
import com.estech.EstechAppBackend.dto.user.CreationUserDTO;
import com.estech.EstechAppBackend.dto.user.UserEmailDTO;
import com.estech.EstechAppBackend.dto.user.UserInfoDTO;
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
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsersAsCreatedUserDTO(), HttpStatus.OK);
    }

    @PostMapping("/new-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody CreationUserDTO creationUserDTO) {
        return new ResponseEntity<>(userService.createNewUser(creationUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/user-info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserInfo(@RequestBody UserEmailDTO email) {
        UserInfoDTO user = userService.getUserInfo(email.getEmail());

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("error: user not found", HttpStatus.NOT_FOUND);
    }
}
