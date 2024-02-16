package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.repository.MentoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentoringService {

    @Autowired
    private MentoringRepository mentoringRepository;

}
