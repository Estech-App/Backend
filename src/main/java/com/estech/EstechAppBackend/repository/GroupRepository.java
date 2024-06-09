package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query(
            value = "DELETE FROM time_table WHERE group_id = ?1",
            nativeQuery = true
    )
    void deleteRelatedTimeTables(Long id);
}