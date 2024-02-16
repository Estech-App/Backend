package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.repository.FreeUsagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreeUsagesService {

    @Autowired
    private FreeUsagesRepository freeUsagesRepository;

}
