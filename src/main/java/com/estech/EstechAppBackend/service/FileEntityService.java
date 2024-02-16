package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileEntityService {

    @Autowired
    private FileEntityRepository fileRepository;

}
