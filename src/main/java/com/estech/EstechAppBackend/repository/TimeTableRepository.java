package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}