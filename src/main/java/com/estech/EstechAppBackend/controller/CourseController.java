package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PostMapping("/new-course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewCourse(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
    }

}
