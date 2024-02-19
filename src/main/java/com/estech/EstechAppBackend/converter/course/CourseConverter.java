package com.estech.EstechAppBackend.converter.course;

import com.estech.EstechAppBackend.dto.course.CourseDTO;
import com.estech.EstechAppBackend.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseConverter {

    public CourseDTO convertCourseEntityToCreatedCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setName(course.getName());
        courseDTO.setAcronym(course.getAcronym());
        courseDTO.setDescription(course.getDescription());

        return courseDTO;
    }

}
