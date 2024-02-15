package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}