package com.estech.EstechAppBackend.controller;

import com.estech.EstechAppBackend.dto.course.CourseDTO;
import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        CourseDTO course = courseService.getCourseById(id);

        if (course == null) {
            return new ResponseEntity<>("error: wrong id", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("/new-course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewCourse(@Valid @RequestBody Course course) {
        return new ResponseEntity<>(courseService.createOrUpdateCourse(course), HttpStatus.CREATED);
    }

    @PutMapping("/update-course")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCourse(@RequestBody Course course) {
        return new ResponseEntity<>(courseService.createOrUpdateCourse(course), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
