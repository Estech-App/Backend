package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {
}