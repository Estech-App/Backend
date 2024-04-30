package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Status;
import com.estech.EstechAppBackend.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByStatus(StatusEnum status);
}