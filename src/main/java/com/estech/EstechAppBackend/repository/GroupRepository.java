package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}