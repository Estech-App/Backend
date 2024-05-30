package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.CheckIn;
import com.estech.EstechAppBackend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findCheckInByUser(UserEntity user);
}
