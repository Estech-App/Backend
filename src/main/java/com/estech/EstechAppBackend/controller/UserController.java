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
        return new ResponseEntity<>(userService.createOrUpdateNewUser(creationUserDTO), HttpStatus.CREATED);
    }

    @PostMapping("/user-info")
    @PreAuthorize("hasRole('ADMIN') || hasRole('SECRETARY')")
    public ResponseEntity<?> getUserInfo(@RequestBody UserEmailDTO email) {
        UserInfoDTO user = userService.getUserInfo(email.getEmail());

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("error: user not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        CreationUserDTO dto = userService.getUserById(id);

        if (dto == null) {
            return new ResponseEntity<>("error: user not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/update-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserById(@RequestBody CreationUserDTO user) {
        return new ResponseEntity<>(userService.createOrUpdateNewUser(user), HttpStatus.OK);

    }

}
