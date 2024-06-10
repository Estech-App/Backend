package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Group;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM users_groups WHERE group_id = :id",
            nativeQuery = true
    )
    void deleteRelationsWithUser(@Param(value = "id") Long id);
}