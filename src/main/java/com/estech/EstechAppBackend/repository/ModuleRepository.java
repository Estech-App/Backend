package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}