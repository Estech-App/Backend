package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

}
