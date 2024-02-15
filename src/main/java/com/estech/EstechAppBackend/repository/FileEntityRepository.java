package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
}