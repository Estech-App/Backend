package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckInService {

    @Autowired
    private CheckInRepository checkInRepository;

}
