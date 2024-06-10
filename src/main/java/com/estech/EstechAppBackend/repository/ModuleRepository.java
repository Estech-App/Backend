package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Course;
import com.estech.EstechAppBackend.model.Module;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findModulesByCourse(Course course);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM users_modules WHERE module_id = :id",
            nativeQuery = true
    )
    void deleteRelationsWithUser(@Param(value = "id") Long id);
}