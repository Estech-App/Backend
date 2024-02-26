package com.estech.EstechAppBackend.repository;

import com.estech.EstechAppBackend.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    @Query(value = "SELECT c from CheckIn c where c.user.id = :id ORDER BY c.id DESC")
    List<CheckIn> getCheckInOrderDesc(Long id);

}