package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<List<UserEntity>> findAllByRole(Role role);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM users_groups WHERE user_id = :id",
            nativeQuery = true
    )
    void deleteRelationsWithGroup(@Param(value = "id") Long id);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM users_modules WHERE user_id = :id",
            nativeQuery = true
    )
    void deleteRelationsWithModule(@Param(value = "id") Long id);
}