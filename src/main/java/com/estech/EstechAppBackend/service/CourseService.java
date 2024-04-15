package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.course.CourseConverter;
import com.estech.EstechAppBackend.dto.course.CourseDTO;
import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseConverter courseConverter;

    public CourseDTO createOrUpdateCourse(Course course) {
        return courseConverter.convertCourseEntityToCreatedCourseDTO(courseRepository.save(course));
    }

    public CourseDTO getCourseById(Integer id) {
        Course course = courseRepository.findById(id).orElse(null);

        if (course == null) {
            return null;
        }

        return courseConverter.convertCourseEntityToCreatedCourseDTO(course);
    }

    public List<CourseDTO> getAllCourses() {
        List<CourseDTO> list = new ArrayList<>();

        courseRepository.findAll().forEach(course -> {
            list.add(courseConverter.convertCourseEntityToCreatedCourseDTO(course));
        });
        return list;
    }

}
