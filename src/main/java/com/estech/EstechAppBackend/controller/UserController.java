package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.user.*;
import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.repository.RoleRepository;
import com.estech.EstechAppBackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

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

    @PostMapping("/new-user/student")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentUserDTO> createStudent(@Valid @RequestBody StudentUserDTO studentUserDTO) {
        StudentUserDTO created = userService.createStudentUser(studentUserDTO);
        return ResponseEntity.created(URI.create("/api/user/new-user/student/" + studentUserDTO.getId())).body(created);
    }

    @PostMapping("/new-user/teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherUserDTO> createTeacher(@Valid @RequestBody TeacherUserDTO teacherUserDTO) {
        TeacherUserDTO created = userService.createTeacherUser(teacherUserDTO);
        return ResponseEntity.created(URI.create("/api/user/new-user/teacher/" + teacherUserDTO.getId())).body(created);
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        CreationUserDTO dto = userService.getUserById(id);

        if (dto == null) {
            return new ResponseEntity<>("error: user not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentUserDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getStudentById(id));
    }

    @GetMapping("/teacher/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherUserDTO> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTeacherById(id));
    }

    @PutMapping("/update-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserById(@RequestBody CreationUserDTO user) {
        return new ResponseEntity<>(userService.createOrUpdateNewUser(user), HttpStatus.OK);
    }

    @GetMapping("/find-by-role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersByRole(@PathVariable Integer roleId) {
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            return new ResponseEntity<>("error: wrong role ID", HttpStatus.BAD_REQUEST);
        }

        List<UserInfoDTO> dto = userService.getAllUsersByRole(role);
        if (dto == null) {
            return new ResponseEntity<>("empty set", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
