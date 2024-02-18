package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Role;
import com.estech.EstechAppBackend.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRolName(RoleEnum role);
}