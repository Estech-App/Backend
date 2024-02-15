package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}