package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}