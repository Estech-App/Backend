package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Mentoring;
import com.estech.EstechAppBackend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {
    List<Mentoring> findMentoringByTeacher(UserEntity teacher);
    List<Mentoring> findMentoringByStudent(UserEntity student);
}