package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(
            value = "DELETE FROM stocks WHERE room_id = ?1",
            nativeQuery = true
    )
    void deleteRelatedStocks(Long id);
}