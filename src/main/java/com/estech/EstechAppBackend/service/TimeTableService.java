package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.repository.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeTableService {

    @Autowired
    private TimeTableRepository timeTableRepository;

}
