package com.estech.EstechAppBackend.service;

import com.estech.EstechAppBackend.converter.StatusConverter;
import com.estech.EstechAppBackend.dto.StatusDTO;
import com.estech.EstechAppBackend.model.Status;
import com.estech.EstechAppBackend.model.enums.StatusEnum;
import com.estech.EstechAppBackend.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StatusConverter statusConverter;

    public void saveStatus(Status status) {
        statusRepository.save(status);
    }

    public List<StatusDTO> getAllStatus() {
        List<StatusDTO> statuses = new ArrayList<>();

        statusRepository.findAll().forEach(status -> {
            statuses.add(statusConverter.convertStatusEntityToStatusDTO(status));
        });
        return statuses;
    }

    public List<Status> getAllStatusEntities() {
        return statusRepository.findAll();
    }

    public Status getStatusByStatusName(StatusEnum statusEnum) {
        return statusRepository.findByStatus(statusEnum).orElse(null);
    }

}
