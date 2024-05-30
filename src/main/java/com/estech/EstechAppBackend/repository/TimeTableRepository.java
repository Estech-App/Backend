package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Group;
import com.estech.EstechAppBackend.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}