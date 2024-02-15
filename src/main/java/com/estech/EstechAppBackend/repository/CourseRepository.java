package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}