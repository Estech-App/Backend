package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Room;
import com.estech.EstechAppBackend.model.RoomTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTimeTableRepository extends JpaRepository<RoomTimeTable, Long> {
}
