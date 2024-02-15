package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.FreeUsages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeUsagesRepository extends JpaRepository<FreeUsages, Long> {
}