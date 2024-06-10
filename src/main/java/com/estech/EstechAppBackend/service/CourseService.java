package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.course.CourseConverter;
import com.estech.EstechAppBackend.dto.course.CourseDTO;
import com.estech.EstechAppBackend.exceptions.AppException;
import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseConverter courseConverter;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ModuleService moduleService;

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

    public void deleteCourse(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException("Course with id " + id + " not found", HttpStatus.NOT_FOUND));

        if (course.getModules() != null) {
            course.getModules().forEach(module -> {
                moduleService.deleteModule(module.getId());
            });
        }
        if (course.getGroups() != null) {
            course.getGroups().forEach(group -> {
                groupService.deleteGroup(group.getId());
            });
        }

        courseRepository.deleteById(id);
    }

}
