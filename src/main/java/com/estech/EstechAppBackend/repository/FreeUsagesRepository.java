package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.FreeUsages;
import com.estech.EstechAppBackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeUsagesRepository extends JpaRepository<FreeUsages, Long> {
    List<FreeUsages> findFreeUsagesByRoom(Room room);
}