package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findModulesByCourse(Course course);
}