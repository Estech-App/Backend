package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}