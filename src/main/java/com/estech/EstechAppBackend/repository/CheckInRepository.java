package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
}